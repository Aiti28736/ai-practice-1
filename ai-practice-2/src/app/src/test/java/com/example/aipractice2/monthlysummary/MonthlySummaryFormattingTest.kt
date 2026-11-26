package com.example.aipractice2.monthlysummary

import org.junit.Assert.assertEquals
import org.junit.Test

class MonthlySummaryFormattingTest {
    @Test
    fun formatsYenWithGroupingAndCurrencyUnit() {
        assertEquals("1,302円", formatYen(1302))
        assertEquals("0円", formatYen(0))
    }
}
