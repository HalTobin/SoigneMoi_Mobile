package com.example.soignemoi.feature.add_note.presentation

sealed class AddNoteEvent {
    data class UpdateTitle(val text: String): AddNoteEvent()
    data class UpdateContent(val text: String): AddNoteEvent()
    object SaveNote: AddNoteEvent()
}