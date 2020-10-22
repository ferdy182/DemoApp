package com.fernandofgallego.navviscodingchallenge.domain

import com.fernandofgallego.navviscodingchallenge.data.Item
import com.fernandofgallego.navviscodingchallenge.ui.main.DataItem
import kotlin.experimental.and

class NumberProcessor {
    companion object {
        const val TWO_LEAST_SIGNIFICANT_BITS = 3.toByte()
        const val ONE_MOST_SIGNIFICANT_BIT = 128.toByte()
        const val MIDDLE_VALUE_BITS = 124.toByte()

    }

    fun parseNumbers(numbers: List<Byte>): List<Item> {
        val list = mutableListOf<Item>()
        numbers.forEach { number ->
            val sectionValue = number and TWO_LEAST_SIGNIFICANT_BITS
            val checkedBit = number and ONE_MOST_SIGNIFICANT_BIT
            val value = (number and MIDDLE_VALUE_BITS).toInt() shr 2

            val item = Item("SECTION$sectionValue", "Item$value", checkedBit == 128.toByte())
            list.add(item)
        }
        return list
    }

    fun parseItems(items: List<Item>): List<DataItem> {
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