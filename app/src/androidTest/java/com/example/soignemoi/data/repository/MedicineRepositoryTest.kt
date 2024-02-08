package com.example.soignemoi.data.repository

import com.example.soignemoi.data.model.Medicine
import com.example.soignemoi.resource.MedicinePlaceholder
import javax.inject.Inject

class MedicineRepositoryTest @Inject constructor(): MedicineRepository {

    override suspend fun getMedicines(): List<Medicine> =
        MedicinePlaceholder.medicineResponsePlaceholder

}