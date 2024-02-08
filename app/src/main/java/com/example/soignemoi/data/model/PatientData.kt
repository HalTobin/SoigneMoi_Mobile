package com.example.soignemoi.data.model

import java.util.Date

data class PatientData(
    val id: Int,
    val name: String,
    val surname: String,
    val appointment: Appointment,
    val doctor: Doctor,
    val specialty: Specialty,
    val startDate: Date,
    val endDate: Date,
    val notes: List<Note>,
    val prescriptions: List<Prescription>
)