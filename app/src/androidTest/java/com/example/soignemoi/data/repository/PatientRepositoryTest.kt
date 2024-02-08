package com.example.soignemoi.data.repository

import android.util.Log
import com.example.soignemoi.data.api.SoigneMoiService
import com.example.soignemoi.data.model.NoteDto
import com.example.soignemoi.data.model.Patient
import com.example.soignemoi.data.model.PatientData
import com.example.soignemoi.feature.prescription.data.NewPrescription
import com.example.soignemoi.resource.PatientPlaceholder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PatientRepositoryTest @Inject constructor(
): PatientRepository {

    private val refreshTrigger = MutableSharedFlow<Unit>()

    override suspend fun getMyPatients(): List<Patient> =
        PatientPlaceholder.patientsResponsePlaceholder

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPatientDetailsAsFlow(patientId: Int): Flow<PatientData?> {
        return refreshTrigger.flatMapLatest {
            flow {
                emit(PatientPlaceholder.patientDataResponsePlaceholder)
            }
        }
    }

    override suspend fun getPatientDetails(patientId: Int): PatientData? =
        PatientPlaceholder.patientDataResponsePlaceholder

    override suspend fun addNote(note: NoteDto): Boolean = true

    override suspend fun savePrescription(prescription: NewPrescription): Boolean = true

    override suspend fun updatePrescriptionEndDate(
        prescriptionId: Int,
        newEndDate: String
    ): Boolean = true

}