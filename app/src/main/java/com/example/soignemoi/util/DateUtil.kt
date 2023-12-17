package com.example.soignemoi.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateUtil {

    val Date.formattedDate: String get() {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("fr", "FR"))
        return dateFormat.format(this)
    }

    fun howManyDays(start: Date, end: Date): Int {
        val durationInMilliseconds = end.time - start.time
        return TimeUnit.MILLISECONDS.toDays(durationInMilliseconds).toInt() + 1
    }

}