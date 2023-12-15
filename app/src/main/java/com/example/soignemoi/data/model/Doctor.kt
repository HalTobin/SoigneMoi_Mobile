package com.example.soignemoi.data.model

data class Doctor(
    val id: Int,
    val name: String,
    val surname: String,
    val registrationNumber: String,
    val specialty: Specialty
)
