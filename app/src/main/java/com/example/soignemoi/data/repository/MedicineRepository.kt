package com.example.soignemoi.data.repository

import com.example.soignemoi.data.api.SoigneMoiService
import com.example.soignemoi.data.model.Medicine
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val api: SoigneMoiService
): MedicineRepository {

    override suspend fun getMedicines(): List<Medicine> {
        try {
            val result = api.getMedicines()
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

interface MedicineRepository {
    suspend fun getMedicines(): List<Medicine>
}