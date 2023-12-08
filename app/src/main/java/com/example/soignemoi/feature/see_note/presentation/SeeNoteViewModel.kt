package com.example.soignemoi.feature.see_note.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SeeNoteViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(SeeNoteState())
    val state = _state.asStateFlow()

    fun onEvent(event: SeeNoteEvent) {
        when (event) {
            // TODO - Add all events
            else -> {}
        }
    }

}