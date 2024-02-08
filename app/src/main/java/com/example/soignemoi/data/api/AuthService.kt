package com.example.soignemoi.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest): Response<TokenResponse>

}

data class LoginRequest(val mail: String, val password: String)

data class TokenResponse(val accessToken: String, val tokenType: String, val role: String, val validity: Long)