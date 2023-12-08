package com.example.soignemoi.feature.patient_list.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PatientsListViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(PatientsListState())
    val state = _state.asStateFlow()

    fun onEvent(event: PatientsListEvent) {
        when (event) {
            // TODO - Add all events
            else -> {}
        }
    }

}