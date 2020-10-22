package com.fernandofgallego.navviscodingchallenge.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernandofgallego.navviscodingchallenge.data.Item
import com.fernandofgallego.navviscodingchallenge.data.Repository
import com.fernandofgallego.navviscodingchallenge.domain.JsonProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.experimental.and

class MainViewModel(private val repository: Repository) : ViewModel() {
    companion object {
        const val TWO_LEAST_SIGNIFICANT_BITS = 3.toByte()
        const val ONE_MOST_SIGNIFICANT_BIT = 128.toByte()
        const val MIDDLE_VALUE_BITS = 124.toByte()

    }
    val data = MutableLiveData<List<DataItem>>()

    suspend fun update() {
        withContext(Dispatchers.IO) {
            val numbers = repository.getNumbers()
            val items = parseNumbers(numbers)
            data.postValue(parseItems(items))
        }
    }

    private fun parseNumbers(numbers: List<Byte>): List<Item> {
        val list = mutableListOf<Item>()
        numbers.forEach {number ->
            val sectionValue = number and TWO_LEAST_SIGNIFICANT_BITS
            val checkedBit = number and ONE_MOST_SIGNIFICANT_BIT
            val value = (number and MIDDLE_VALUE_BITS).toInt() shr 2

            val item = Item("SECTION$sectionValue", "Item$value", checkedBit == 128.toByte())
            list.add(item)
        }
        return list
    }

    private fun parseItems(items: List<Item>): List<DataItem> {
        val dataItems = mutableListOf<DataItem>()
        items.groupBy {
            it.section
        }.toSortedMap().forEach { (key, items) ->
            dataItems.add(DataItem.Section(key))
            items.forEach {
                dataItems.add(DataItem.Item(it.value, it.checked))
            }
        }
        return dataItems
    }
}