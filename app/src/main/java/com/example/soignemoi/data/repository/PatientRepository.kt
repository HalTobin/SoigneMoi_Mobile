package com.example.soignemoi.data.repository

import android.util.Log
import com.example.soignemoi.data.api.SoigneMoiService
import com.example.soignemoi.data.model.Note
import com.example.soignemoi.data.model.NoteDto
import com.example.soignemoi.data.model.Patient
import com.example.soignemoi.data.model.PatientData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val api: SoigneMoiService
): PatientRepository {

    private val refreshTrigger = MutableStateFlow(Unit)

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
    override suspend fun getPatientDetailsAsFlow(patientId: Int): Flow<PatientData?> =
        refreshTrigger.flatMapConcat {
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

    override suspend fun getPatientDetails(patientId: Int): PatientData? {
        return try {
            val result = api.getPatientDetails(patientId)
            Log.i("API - Details", result.body()?.toString() ?: "null")
            result.body()
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun addNote(note: NoteDto): Boolean {
        return try {
            val request = api.addNote(note)
            if (request.isSuccessful) {
                // Trigger refresh after successful note addition
                refreshTrigger.value = Unit
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
    suspend fun getPatientDetailsAsFlow(patientId: Int): Flow<PatientData?>
    suspend fun getPatientDetails(patientId: Int): PatientData?
    suspend fun addNote(note: NoteDto): Boolean
}