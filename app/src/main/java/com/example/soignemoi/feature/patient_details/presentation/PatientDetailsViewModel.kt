package com.example.soignemoi.feature.patient_details.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PatientDetailsViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(PatientDetailsState())
    val state = _state.asStateFlow()

    fun onEvent(event: PatientDetailsEvent) {
        when (event) {
            // TODO - Add all events
            else -> {}
        }
    }

}