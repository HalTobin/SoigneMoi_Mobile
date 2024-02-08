package com.example.soignemoi.util

import com.example.soignemoi.resource.ConstPlaceholder.END_DATE
import com.example.soignemoi.resource.ConstPlaceholder.START_DATE
import com.example.soignemoi.util.DateUtil.formattedDate
import com.example.soignemoi.util.DateUtil.howManyDays
import com.example.soignemoi.util.DateUtil.toDate
import org.junit.Assert
import org.junit.Test
import java.util.Calendar

class DateUtilTest {

    @Test
    fun `check format date to String from Date`() {
        // Given
        val expected = "17 décembre 2023"

        // When
        val actual = START_DATE.formattedDate

        // Assert
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `check to Date from String`() {
        // Given
        val dateStr = "2023-12-27T23:00:00.000+00:00"

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.DAY_OF_MONTH, 28)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val expected = calendar.time

        // When
        val actual = dateStr.toDate

        // Assert
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `check count of days between two dates`() {
        // Given
        val start = START_DATE
        val end = END_DATE
        val expected = 12

        // When
        val actual = howManyDays(start, end)

        // Assert
        Assert.assertEquals(expected, actual)
    }

}