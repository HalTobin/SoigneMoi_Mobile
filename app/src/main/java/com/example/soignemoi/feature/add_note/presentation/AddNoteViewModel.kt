package com.example.soignemoi.feature.add_note.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soignemoi.data.model.NoteDto
import com.example.soignemoi.data.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(AddNoteState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("patientId")?.let { loadPatient(it) }
    }

    private fun loadPatient(patientId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(patientId = patientId, patientData = patientRepository.getPatientDetails(patientId)) }
    }

    fun onEvent(event: AddNoteEvent) {
        when (event) {
            is AddNoteEvent.UpdateTitle -> _state.update { it.copy(title = event.text) }
            is AddNoteEvent.UpdateContent -> _state.update { it.copy(content = event.text) }
            is AddNoteEvent.SaveNote -> viewModelScope.launch {
                _state.value.patientData?.let { data ->
                    if (patientRepository.addNote(NoteDto(
                        appointmentId = data.appointment.id,
                        doctorId = data.doctor.id,
                        userId = _state.value.patientId,
                        title = _state.value.title,
                        content = _state.value.content
                    ))) _eventFlow.emit(UiEvent.QuitAddNoteScreen)
                }
            }
        }
    }

    sealed class UiEvent {
        object QuitAddNoteScreen: UiEvent()
    }

}