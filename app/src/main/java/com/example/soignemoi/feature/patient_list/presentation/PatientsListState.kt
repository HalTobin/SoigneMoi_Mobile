package com.example.soignemoi.feature.patient_list.presentation

import com.example.soignemoi.data.model.Patient

data class PatientsListState(
    val loading: Boolean = false,
    val patients: List<Patient> = emptyList()
)