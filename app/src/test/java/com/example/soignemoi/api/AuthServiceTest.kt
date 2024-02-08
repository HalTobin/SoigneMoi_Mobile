package com.example.soignemoi.api

import android.content.SharedPreferences
import android.util.Log
import com.example.soignemoi.data.api.AuthService
import com.example.soignemoi.data.api.LoginRequest
import com.example.soignemoi.data.api.SessionManager
import com.example.soignemoi.resource.AuthPlaceholder.TOKEN_RESPONSE_JSON
import com.example.soignemoi.resource.AuthPlaceholder.tokenResponsePlaceholder
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AuthServiceTest {

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
        .create(AuthService::class.java)

    private val sp: SharedPreferences = mockk()

    private val sessionManager = SessionManager(api, sp, sp)

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0

        coEvery { sp.edit() } returns null
    }

    @After
    fun clear() {
        clearAllMocks()
        mockWebServer.shutdown()
    }

    @Test
    fun `check token response deserialization`() = runBlocking {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(TOKEN_RESPONSE_JSON))
        val response = sessionManager.connect(LoginRequest("TEST", "TEST"), false)

        // When
        assertEquals(tokenResponsePlaceholder, response)
    }

}