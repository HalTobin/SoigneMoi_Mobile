package com.example.soignemoi.feature.prescription.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NewPrescription(
    val id: Int?,
    val appointmentId: Int,
    val start: String,
    val end: String,
    val entries: List<NewEntry>
)

val Date.asString: String get() {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}
