package com.example.soignemoi.data.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Note(
    val id: Int,
    val appointmentId: Int,
    val doctorId: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val date: Date
) {

    fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("fr", "FR"))
        return dateFormat.format(date)
    }

}

data class NoteDto(
    val appointmentId: Int,
    val doctorId: Int,
    val userId: Int,
    val title: String,
    val content: String,
)