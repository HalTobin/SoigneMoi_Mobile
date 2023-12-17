package com.example.soignemoi.feature.prescription.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soignemoi.data.repository.MedicineRepository
import com.example.soignemoi.data.repository.PatientRepository
import com.example.soignemoi.data.repository.PrescriptionRepository
import com.example.soignemoi.feature.prescription.data.NewPrescription
import com.example.soignemoi.feature.prescription.data.asString
import com.example.soignemoi.util.DateUtil.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrescriptionViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository,
    private val prescriptionRepository: PrescriptionRepository,
    private val patientRepository: PatientRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(PrescriptionState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("appointmentId")?.let { appointmentId ->
            viewModelScope.launch {
                _state.update { it.copy(
                    appointmentId = appointmentId,
                    medicines = medicineRepository.getMedicines()
                ) }
                savedStateHandle.get<Int>("prescriptionId")?.let { prescriptionId ->
                    prescriptionRepository.getPrescription(prescriptionId)?.let { prescription ->
                        _state.update { it.copy(
                            prescriptionId = prescription.id,
                            entries = prescription.entries,
                            dateStart = prescription.start.toDate,
                            dateEnd = prescription.end.toDate
                        ) }
                    }
                }
            }
        }
    }

    fun onEvent(event: PrescriptionEvent) {
        when (event) {
            is PrescriptionEvent.SelectStartDate -> _state.update { it.copy(dateStart = event.date) }
            is PrescriptionEvent.SelectEndDate -> _state.update { it.copy(dateEnd = event.date) }
            is PrescriptionEvent.AddEntry -> {
                val entries = _state.value.entries.toMutableList()
                entries.add(event.entry)
                _state.update { it.copy(entries = entries.toList()) }
            }
            is PrescriptionEvent.Save -> viewModelScope.launch {
                if (_state.value.canSave()) {
                    if (_state.value.prescriptionId == null) {
                        val newPrescription = NewPrescription(
                            id = _state.value.prescriptionId,
                            appointmentId = state.value.appointmentId!!,
                            start = state.value.dateStart!!.asString,
                            end = state.value.dateEnd!!.asString,
                            entries = state.value.entries
                        )
                        patientRepository.savePrescription(newPrescription)
                    }
                    else {
                        if (_state.value.prescriptionId != null && _state.value.dateEnd != null) patientRepository.updatePrescriptionEndDate(
                            _state.value.prescriptionId!!,
                            _state.value.dateEnd!!.asString
                        )
                    }
                }
            }
        }
    }

}