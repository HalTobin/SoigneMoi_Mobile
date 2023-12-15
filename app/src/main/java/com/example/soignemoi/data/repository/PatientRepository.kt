package com.example.soignemoi.data.repository

import android.util.Log
import com.example.soignemoi.data.api.SoigneMoiService
import com.example.soignemoi.data.model.Patient
import com.example.soignemoi.data.model.PatientData
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val api: SoigneMoiService
): PatientRepository {

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

    override suspend fun getPatientDetails(patientId: Int): PatientData? {
        return try {
            val result = api.getPatientDetails(patientId)
            Log.i("API - Details", result.body()?.toString() ?: "null")
            result.body()
        } catch (e: Exception) {
            null
        }
    }

}

interface PatientRepository {
    suspend fun getMyPatients(): List<Patient>
    suspend fun getPatientDetails(patientId: Int): PatientData?
}