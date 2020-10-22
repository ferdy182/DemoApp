package com.fernandofgallego.navviscodingchallenge.domain

import com.fernandofgallego.navviscodingchallenge.data.Item
import com.fernandofgallego.navviscodingchallenge.data.DataItem
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

class NumberProcessor {
    companion object {
        const val TWO_LEAST_SIGNIFICANT_BITS = 3.toByte()                   // 00000011
        const val ONE_MOST_SIGNIFICANT_BIT = 128.toByte()                   // 10000000
        val MIDDLE_VALUE_BITS =
            (TWO_LEAST_SIGNIFICANT_BITS or ONE_MOST_SIGNIFICANT_BIT).inv()  // 01111100

    }

    /*
    Converts a list of bytes into Items with section, value and check
     */
    fun parseNumbers(numbers: List<Byte>): List<Item> {
        val list = mutableListOf<Item>()
        numbers.forEach { number ->
            if(validateInput(number)) {
                val sectionValue = number and TWO_LEAST_SIGNIFICANT_BITS
                val checkedBit = number and ONE_MOST_SIGNIFICANT_BIT
                val value =
                    (number and MIDDLE_VALUE_BITS).toInt() shr 2 // shift right 2 positions to get rid of the two least significant bits

                val item = Item(
                    "SECTION$sectionValue",
                    "Item$value",
                    checkedBit == ONE_MOST_SIGNIFICANT_BIT
                )
                list.add(item)
            }
        }
        return list
    }

    /*
    Converts a list of Items with section, value and check into DataItems for the list adapter
     */
    fun convertItems(items: List<Item>): List<DataItem> {
        val dataItems = mutableListOf<DataItem>()
        items.groupBy {
            it.section
        }.toSortedMap().forEach { (key, items) ->
            dataItems.add(DataItem.Section(key))
            items.sortedBy { it.value }.forEach {
                dataItems.add(DataItem.Item(it.value, it.checked))
            }

        }
        return dataItems
    }

    private fun validateInput(input: Byte): Boolean = input in 0..255
}