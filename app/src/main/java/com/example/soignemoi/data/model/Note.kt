package com.example.soignemoi.data.model

import java.util.Date

data class Note(
    val id: Int,
    val appointmentId: Int,
    val doctorId: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val date: Date
)

data class NoteDto(
    val appointmentId: Int,
    val doctorId: Int,
    val userId: Int,
    val title: String,
    val content: String,
)