package com.example.soignemoi.data.repository

import com.example.soignemoi.feature.prescription.data.NewPrescription
import com.example.soignemoi.resource.PrescriptionPlaceholder
import javax.inject.Inject

class PrescriptionRepositoryTest @Inject constructor(): PrescriptionRepository {

    override suspend fun getPrescription(prescriptionId: Int): NewPrescription? =
        PrescriptionPlaceholder.newPrescription

}