package com.example.soignemoi.data.repository

import com.example.soignemoi.data.network.LoginRequest
import com.example.soignemoi.data.network.SoigneMoiApiService
import com.example.soignemoi.data.network.TokenResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: SoigneMoiApiService
): AuthRepository {

    override suspend fun connect(credentials: LoginRequest): TokenResponse {
        val response = api.login(credentials)
        if (response.isSuccessful) {
            // Extract the token from the response
            val tokenResponse = response.body()

            if (tokenResponse == null || tokenResponse.role != "Doctor") throw IllegalStateException("Connection failed")

            // Return the token or handle it as needed
            return tokenResponse
        } else {
            // Handle the error (e.g., show an error message)
            throw IllegalStateException("Login failed: ${response.errorBody()?.string()}")
        }
    }

}

interface AuthRepository {
    suspend fun connect(credentials: LoginRequest): TokenResponse
}