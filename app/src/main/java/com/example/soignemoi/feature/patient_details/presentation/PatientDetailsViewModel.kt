package com.example.soignemoi.feature.patient_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soignemoi.data.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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

    private var detailsJob: Job? = null

    init {
        savedStateHandle.get<Int>("patientId")?.let { id ->
            viewModelScope.launch {
                _state.update { it.copy(
                    patientId = id,
                    patientData = patientRepository.getPatientDetails(id)
                ) }
            }
            loadPatient(id)
        }
    }

    private fun loadPatient(patientId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(patientId = patientId) }
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch(Dispatchers.IO) {
            patientRepository.getPatientDetailsAsFlow(patientId).collect { data ->
                _state.update { it.copy(patientData = data) }
            }
        }
    }

    fun onEvent(event: PatientDetailsEvent) {
        when (event) {
            else -> {}
        }
    }

}