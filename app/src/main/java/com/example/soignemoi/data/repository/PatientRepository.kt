package com.example.soignemoi.data.repository

import android.util.Log
import com.example.soignemoi.data.api.SoigneMoiService
import com.example.soignemoi.data.model.NoteDto
import com.example.soignemoi.data.model.Patient
import com.example.soignemoi.data.model.PatientData
import com.example.soignemoi.feature.prescription.data.NewPrescription
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val api: SoigneMoiService
): PatientRepository {

    private val refreshTrigger = MutableSharedFlow<Unit>()

    override suspend fun getMyPatients(): List<Patient> {
        try {
            val result = api.getMyPatients()
            if (result.isSuccessful) {
                result.body()?.let { return it }
                return emptyList()
            }
            else return emptyList()
        } catch (e: Exception) {
            return emptyList()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPatientDetailsAsFlow(patientId: Int): Flow<PatientData?> {
        return refreshTrigger.flatMapLatest {
            flow {
                try {
                    val result = api.getPatientDetails(patientId)
                    Log.i("API - Details", result.body()?.toString() ?: "null")
                    emit(result.body())
                } catch (e: Exception) {
                    Log.w("API - Error", e.message.toString())
                }
            }
        }
    }

    override suspend fun getPatientDetails(patientId: Int): PatientData? {
        return try {
            val result = api.getPatientDetails(patientId)
            Log.i("API - Details", result.body()?.toString() ?: "null")
            result.body()
        } catch (e: Exception) {
            Log.w("API - ERROR", e.message ?: "")
            return null
        }
    }

    override suspend fun addNote(note: NoteDto): Boolean {
        return try {
            val request = api.addNote(note)
            if (request.isSuccessful) {
                // Trigger refresh after successful note addition
                refreshTrigger.emit(Unit)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun savePrescription(prescription: NewPrescription): Boolean {
        return try {
            val request = api.savePrescription(prescription)
            if (request.isSuccessful) {
                // Trigger refresh after successful note addition
                refreshTrigger.emit(Unit)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updatePrescriptionEndDate(
        prescriptionId: Int,
        newEndDate: String
    ): Boolean {
        return try {
            val request = api.updatePrescriptionEndDate(prescriptionId, newEndDate)
            if (request.isSuccessful) {
                // Trigger refresh after successful note addition
                refreshTrigger.emit(Unit)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

}

interface PatientRepository {
    suspend fun getMyPatients(): List<Patient>
    fun getPatientDetailsAsFlow(patientId: Int): Flow<PatientData?>
    suspend fun getPatientDetails(patientId: Int): PatientData?
    suspend fun addNote(note: NoteDto): Boolean
    suspend fun savePrescription(prescription: NewPrescription): Boolean
    suspend fun updatePrescriptionEndDate(prescriptionId: Int, newEndDate: String): Boolean
}