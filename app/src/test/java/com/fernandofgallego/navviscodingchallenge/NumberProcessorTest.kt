package com.fernandofgallego.navviscodingchallenge

import com.fernandofgallego.navviscodingchallenge.domain.NumberProcessor
import org.junit.Test

import org.junit.Assert.*

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


}