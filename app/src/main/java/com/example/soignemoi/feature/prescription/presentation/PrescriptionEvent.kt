package com.example.soignemoi.feature.prescription.presentation

import com.example.soignemoi.feature.prescription.data.NewEntry
import java.util.Date

sealed class PrescriptionEvent {
    data class SelectStartDate(val date: Date): PrescriptionEvent()
    data class SelectEndDate(val date: Date): PrescriptionEvent()
    data class AddEntry(val entry: NewEntry): PrescriptionEvent()
    object Save: PrescriptionEvent()
}