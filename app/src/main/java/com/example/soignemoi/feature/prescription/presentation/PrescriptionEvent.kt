package com.example.soignemoi.feature.prescription.presentation

import java.util.Date

sealed class PrescriptionEvent {
    data class SelectStartDate(val date: Date): PrescriptionEvent()
    data class SelectEndDate(val date: Date): PrescriptionEvent()
}