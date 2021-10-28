package com.gonzales.mark.n_puzzle

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeUtilTest {
    @Test
    fun `Check if display is 0 00 00 if time is 0 seconds`() {
        val numSeconds = 0L
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "0:00:00", TimeUtil.displayTime(numSeconds))
    }

    @Test
    fun `Check if display is of the form 0 00 ss if time is below a minute but greater than 0 seconds`() {
        val numSeconds = 59L
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "0:00:59", TimeUtil.displayTime(numSeconds))
    }

    @Test
    fun `Check if display is of the form 0 01 00 if time is equal to a minute`() {
        val numSeconds = 60L
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "0:01:00", TimeUtil.displayTime(numSeconds))
    }

    @Test
    fun `Check if display is of the form 0 mm 00 if time is below an hour and is an exact multiple of a minute`() {
        val numSeconds = 3540L
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "0:59:00", TimeUtil.displayTime(numSeconds))
    }

    @Test
    fun `Check if display is of the form 0 mm ss if time is below an hour and is not an exact multiple of a minute`() {
        val numSeconds = 3559L
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "0:59:19", TimeUtil.displayTime(numSeconds))
    }

    @Test
    fun `Check if display is of the form 1 00 00 if time is equal to an hour`() {
        val numSeconds = 3600L
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "1:00:00", TimeUtil.displayTime(numSeconds))
    }

    @Test
    fun `Check if display is of the form h 00 ss if time converts to a multiple of an hour and some seconds`() {
        val numSeconds = 7250L
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "2:00:50", TimeUtil.displayTime(numSeconds))
    }

    @Test
    fun `Check if display is of the form h mm 00 if time converts to a multiple of an hour and some minutes`() {
        val numSeconds = 7320L
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "2:02:00", TimeUtil.displayTime(numSeconds))
    }

    @Test
    fun `Check if display is of the form h mm ss if time converts to some hours, minutes, and seconds`() {
        val numSeconds = 7321L
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "2:02:01", TimeUtil.displayTime(numSeconds))
    }

    @Test
    fun `Check if the largest number of seconds handled by the system can be properly displayed`() {
        val numSeconds = Long.MAX_VALUE
        val errorMessage = numSeconds.toString()

        assertEquals(errorMessage, "2562047788015215:30:07", TimeUtil.displayTime(numSeconds))
    }
}