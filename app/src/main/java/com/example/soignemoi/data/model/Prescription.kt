package com.example.soignemoi.data.model

import java.util.Date

data class Prescription(
    val id: Int,
    val appointmentId: Int,
    val start: Date,
    val end: Date,
    val entries: List<PrescriptionEntry>,
) {

    fun getMedicinesAsString(): String {
        var result = ""
        entries.forEachIndexed { index, entry ->
            result += entry.medicine.title
            if (index < entries.lastIndex) result += ", "
        }
        return result
    }

}
