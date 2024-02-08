package com.example.soignemoi.api

import android.util.Log
import com.example.soignemoi.data.api.SoigneMoiService
import com.example.soignemoi.data.repository.PrescriptionRepositoryImpl
import com.example.soignemoi.resource.PrescriptionPlaceholder.NEW_PRESCRIPTION
import com.example.soignemoi.resource.PrescriptionPlaceholder.newPrescription
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PrescriptionRepositoryTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SoigneMoiService::class.java)

    private val prescriptionRepository = PrescriptionRepositoryImpl(api)

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
    }

    @After
    fun clear() {
        clearAllMocks()
        mockWebServer.shutdown()
    }

    @Test
    fun `check medicine list response deserialization`() = runBlocking {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(NEW_PRESCRIPTION))

        // When
        val response = prescriptionRepository.getPrescription(1)

        // Assert
        Assert.assertEquals(newPrescription, response)
    }

}