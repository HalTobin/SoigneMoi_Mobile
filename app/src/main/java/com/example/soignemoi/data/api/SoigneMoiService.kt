package com.example.soignemoi.data.api

import com.example.soignemoi.data.model.Patient
import retrofit2.Response
import retrofit2.http.GET

interface SoigneMoiService {

    @GET("doctor/my_patients")
    suspend fun getMyPatients(): Response<List<Patient>>

}