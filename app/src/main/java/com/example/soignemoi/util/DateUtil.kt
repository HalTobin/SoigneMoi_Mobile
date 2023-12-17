package com.example.soignemoi.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateUtil {

    val Date.formattedDate: String get() {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("fr", "FR"))
        return dateFormat.format(this)
    }

    val String.toDate: Date @SuppressLint("SimpleDateFormat")
    get() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        return dateFormat.parse(this) ?: throw IllegalArgumentException("Invalid date format: $this")
    }

    fun howManyDays(start: Date, end: Date): Int {
        val durationInMilliseconds = end.time - start.time
        return TimeUnit.MILLISECONDS.toDays(durationInMilliseconds).toInt() + 1
    }

}