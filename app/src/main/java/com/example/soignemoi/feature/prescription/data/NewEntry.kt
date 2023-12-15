package com.example.soignemoi.feature.prescription.data

import com.example.soignemoi.data.model.Frequency
import com.example.soignemoi.data.model.Medicine

data class NewEntry(
    val dosage: String = "1",
    val frequency: Frequency = Frequency.DAILY,
    val note: String = "",
    val medicine: Medicine? = null
) {
    val isFilled: Boolean get() {
        dosage.toIntOrNull()?.let { dosageInt ->
            return (dosageInt != 0 && medicine != null)
        }
        return false
    }
}