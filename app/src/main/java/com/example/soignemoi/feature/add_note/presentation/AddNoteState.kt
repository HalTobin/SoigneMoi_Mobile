package com.example.soignemoi.feature.add_note.presentation

import com.example.soignemoi.data.model.PatientData

data class AddNoteState(
    val loading: Boolean = false,
    val patientId: Int = -1,
    val patientData: PatientData? = null,
    val title: String = "",
    val content: String = ""
)