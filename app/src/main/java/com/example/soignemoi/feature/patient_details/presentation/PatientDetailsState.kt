package com.example.soignemoi.feature.patient_details.presentation

import com.example.soignemoi.data.model.PatientData

data class PatientDetailsState(
    val loading: Boolean = false,
    val patientId: Int? = null,
    val patientData: PatientData? = null
)