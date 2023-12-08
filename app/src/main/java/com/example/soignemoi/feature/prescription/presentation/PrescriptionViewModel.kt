package com.example.soignemoi.feature.prescription.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PrescriptionViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(PrescriptionState())
    val state = _state.asStateFlow()

    fun onEvent(event: PrescriptionEvent) {
        when (event) {
            // TODO - Add all events
            else -> {}
        }
    }

}