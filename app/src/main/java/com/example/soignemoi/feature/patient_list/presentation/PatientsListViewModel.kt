package com.example.soignemoi.feature.patient_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soignemoi.data.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsListViewModel @Inject constructor(
    private val patientRepository: PatientRepository
): ViewModel() {

    private val _state = MutableStateFlow(PatientsListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(patients = patientRepository.getMyPatients()) }
        }
    }

    fun onEvent(event: PatientsListEvent) {
        when (event) {
            // TODO - Add all events
            else -> {}
        }
    }

}