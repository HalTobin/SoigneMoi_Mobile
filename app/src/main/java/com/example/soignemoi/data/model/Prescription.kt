package com.example.soignemoi.data.model

import java.util.Date

data class Prescription(
    val id: Int?,
    val appointmentId: Int,
    val start: Date,
    val end: Date,
    val prescriptionsEntries: List<PrescriptionEntry>,
)
