package com.example.soignemoi.feature.prescription.presentation

import com.example.soignemoi.data.model.Medicine
import com.example.soignemoi.data.model.PatientData
import com.example.soignemoi.data.model.PrescriptionEntry
import com.example.soignemoi.feature.prescription.data.NewEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PrescriptionState(
    val loading: Boolean = false,
    val prescriptionId: Int? = null,
    val appointmentId: Int? = null,
    val entries: List<NewEntry> = emptyList(),
    val dateStart: Date? = null,
    val dateEnd: Date? = null,
    val medicines: List<Medicine> = emptyList(),
    val edited: Boolean = false
) {

    val startFormatted: String get() = simpleDateFormat(dateStart) ?: ""

    val endFormatted: String get() = simpleDateFormat(dateEnd) ?: ""

    private fun simpleDateFormat(date: Date?): String? {
        date?.let {
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("fr", "FR"))
            return dateFormat.format(date)
        }
        return null
    }

    fun canSave(): Boolean =
        ((dateStart != null) && (dateEnd != null) && entries.isNotEmpty() && (appointmentId != null))

}