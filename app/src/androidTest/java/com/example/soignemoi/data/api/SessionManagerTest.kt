package com.example.soignemoi.data.api

class SessionManagerTest: SessionManager {
    override suspend fun autoConnect(): String {
        val credentials = loadCredentials()
        val response = connect(credentials, false)
        return response.accessToken
    }

    override suspend fun connect(credentials: LoginRequest, save: Boolean): TokenResponse {
        return if (credentials == doctorCredentials) TokenResponse(
            accessToken = "TOKEN",
            tokenType = "Bearer:",
            role = "Doctor",
            validity = 150000L
        ) else TokenResponse(
            accessToken = "TOKEN",
            tokenType = "Bearer:",
            role = "Visitor",
            validity = 150000L
        )
    }

    override fun loadCredentials(): LoginRequest = doctorCredentials

    override var rememberLogin: Boolean = true
    override var token: String = "TOKEN"

    companion object {
        const val DOCTOR_MAIL = "test@mail"
        const val DOCTOR_PASSWORD = "testPsw"
        val doctorCredentials = LoginRequest(DOCTOR_MAIL, DOCTOR_PASSWORD)
    }

}