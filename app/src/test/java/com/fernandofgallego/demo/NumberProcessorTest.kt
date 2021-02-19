package com.fernandofgallego.demo

import com.fernandofgallego.demo.data.DataItem
import com.fernandofgallego.demo.data.Item
import com.fernandofgallego.demo.domain.NumberProcessor
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NumberProcessorTest {
    private val processor = NumberProcessor()

    @Test
    fun emptyList() {
        val r = processor.parseNumbers(listOf())
        assertTrue(r.isEmpty())
    }

    @Test
    fun parsing_isCorrect() {
        val r = processor.parseNumbers(listOf(5.toByte()))
        assertTrue(r.isNotEmpty())

        val item = r[0]
        assertEquals("SECTION1", item.section)
        assertEquals("Item1", item.value)
        assertFalse(item.checked)
    }

    @Test
    fun parsingBigNumber_isCorrect() {
        val r = processor.parseNumbers(listOf(Int.MAX_VALUE.toByte()))
        assertTrue(r.isEmpty())
    }

    @Test
    fun convertItemIntoDataItem() {
        val r = processor.convertItems(listOf(Item("SECTION1", "Item1", true)))
        assertTrue(r.contains(DataItem.Section("SECTION1")))
        assertTrue(r.contains(DataItem.Item("Item1", true)))
    }


}