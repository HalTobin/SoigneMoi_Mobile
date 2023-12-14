package com.example.soignemoi.feature.patient_details.presentation

import androidx.lifecycle.SavedStateHandle
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
class PatientDetailsViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(PatientDetailsState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("patientId")?.let { loadPatient(it) }
    }

    private fun loadPatient(patientId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(patientId = patientId) }
        patientRepository.getPatientDetails(patientId)
    }

    fun onEvent(event: PatientDetailsEvent) {
        when (event) {
            else -> {}
        }
    }

}