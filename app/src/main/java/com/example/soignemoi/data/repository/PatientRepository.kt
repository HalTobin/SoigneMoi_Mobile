package com.example.soignemoi.data.repository

import com.example.soignemoi.data.api.SoigneMoiService
import com.example.soignemoi.data.model.Patient
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

}

interface PatientRepository {
    suspend fun getMyPatients(): List<Patient>
}