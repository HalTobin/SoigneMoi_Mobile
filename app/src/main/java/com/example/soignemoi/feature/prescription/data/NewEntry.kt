package com.example.soignemoi.feature.prescription.data

import com.example.soignemoi.data.model.Frequency
import com.example.soignemoi.data.model.Medicine

data class NewEntry(
    val dosage: String = "1",
    val frequency: Int = Frequency.DAILY.id,
    val note: String = "",
    val medicineId: Int? = null
) {
    val isFilled: Boolean get() {
        dosage.toIntOrNull()?.let { dosageInt ->
            return (dosageInt != 0 && medicineId != null)
        }
        return false
    }
}