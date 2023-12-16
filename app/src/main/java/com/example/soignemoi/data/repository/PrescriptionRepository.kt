package com.example.soignemoi.data.repository

import com.example.soignemoi.data.api.SoigneMoiService
import com.example.soignemoi.feature.prescription.data.NewPrescription
import javax.inject.Inject

class PrescriptionRepositoryImpl @Inject constructor(
    private val api: SoigneMoiService
): PrescriptionRepository {

    override suspend fun getPrescription(prescriptionId: Int): NewPrescription? {
        try {
            val result = api.getPrescription(prescriptionId)
            if (result.isSuccessful) {
                result.body()?.let { return it }
                return null
            }
            else return null
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun savePrescription(prescription: NewPrescription) {
        api.savePrescription(prescription)
    }

}

interface PrescriptionRepository {
    suspend fun getPrescription(prescriptionId: Int): NewPrescription?

    suspend fun savePrescription(prescription: NewPrescription)
}