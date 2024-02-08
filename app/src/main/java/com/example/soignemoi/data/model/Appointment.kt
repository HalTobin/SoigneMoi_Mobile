package com.example.soignemoi.data.model

import java.util.Date

data class Appointment(
    val id: Int,
    val visitorId: Int,
    val startDate: Date,
    val endDate: Date,
    val reason: String,
    val specialty: String,
    val doctor: String
)
