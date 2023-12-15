package com.example.soignemoi.feature.prescription.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soignemoi.data.model.PrescriptionEntry
import com.example.soignemoi.data.repository.MedicineRepository
import com.example.soignemoi.data.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrescriptionViewModel @Inject constructor(
    val patientRepository: PatientRepository,
    val medicineRepository: MedicineRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(PrescriptionState())
    val state = _state.asStateFlow()

    private var detailsJob: Job? = null

    init {
        savedStateHandle.get<Int>("patientId")?.let { id ->
            viewModelScope.launch {
                _state.update { it.copy(
                    patientId = id,
                    patientData = patientRepository.getPatientDetails(id),
                    medicines = medicineRepository.getMedicines()
                ) }
                savedStateHandle.get<Int>("prescriptionId")?.let { prescriptionId ->
                    _state.value.patientData?.let { data ->
                        val prescription = data.prescriptions.find { it.id == prescriptionId }
                        _state.update { it.copy(
                            prescriptionId = prescriptionId,
                            entries = prescription?.prescriptionsEntries ?: emptyList(),
                            dateStart = prescription?.start,
                            dateEnd = prescription?.end
                        ) }
                    }
                }
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

    fun onEvent(event: PrescriptionEvent) {
        when (event) {
            is PrescriptionEvent.SelectStartDate -> _state.update { it.copy(dateStart = event.date) }
            is PrescriptionEvent.SelectEndDate -> _state.update { it.copy(dateEnd = event.date) }
        }
    }

}