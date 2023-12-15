package com.example.soignemoi.data.api

import com.example.soignemoi.data.model.Patient
import com.example.soignemoi.data.model.PatientData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SoigneMoiService {

    @GET("doctor/my_patients")
    suspend fun getMyPatients(): Response<List<Patient>>

    @GET("doctor/patient_details")
    suspend fun getPatientDetails(@Query("patientId") patientId: Int): Response<PatientData>

}