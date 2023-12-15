package com.example.soignemoi.data.model

data class PrescriptionEntry(
    val id: Int,
    val prescriptionId: Int,
    val dosage: Int,
    val frequency: Frequency,
    val note: String,
    val medicine: Medicine
)

enum class Frequency { DAILY, WEEKLY, MONTHLY }
