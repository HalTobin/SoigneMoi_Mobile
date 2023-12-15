package com.example.soignemoi.data.api

import com.example.soignemoi.data.model.Medicine
import com.example.soignemoi.data.model.Note
import com.example.soignemoi.data.model.NoteDto
import com.example.soignemoi.data.model.Patient
import com.example.soignemoi.data.model.PatientData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SoigneMoiService {

    @GET("doctor/my_patients")
    suspend fun getMyPatients(): Response<List<Patient>>

    @GET("doctor/patient_details")
    suspend fun getPatientDetails(@Query("patientId") patientId: Int): Response<PatientData>

    @POST("doctor/new_note")
    suspend fun addNote(@Body noteDto: NoteDto): Response<Note?>

    @GET("doctor/medicines")
    suspend fun getMedicines(): Response<List<Medicine>>

}