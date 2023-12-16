package com.example.soignemoi.feature.prescription.data

import java.util.Date

data class NewPrescription(
    val id: Int?,
    val appointmentId: Int,
    val start: Date,
    val end: Date,
    val entries: List<NewEntry>
)
