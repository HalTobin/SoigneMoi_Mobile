package com.example.soignemoi.data.api

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.soignemoi.util.PreferencesKey
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    private val api: AuthService,
    private val sp: SharedPreferences,
    private val securedSp: SharedPreferences
): SessionManager {

    override suspend fun autoConnect(): String {
        val credentials = loadCredentials()
        val response = connect(credentials, false)
        return response.accessToken
    }

    override suspend fun connect(credentials: LoginRequest, save: Boolean): TokenResponse {
        if (save) saveCredentials(credentials)
        try {
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
        } catch (exception: Exception) {
            Log.w("AUTH", exception.message ?: "")
            throw IllegalStateException()
        }
    }

    private fun saveCredentials(credentials: LoginRequest) = securedSp.edit {
        putString(PreferencesKey.LOGIN_USER, credentials.mail)
        putString(PreferencesKey.LOGIN_PASSWORD, credentials.password)
        apply()
    }

    override fun loadCredentials(): LoginRequest = LoginRequest(
        mail = securedSp.getString(PreferencesKey.LOGIN_USER, "") ?: "",
        password = securedSp.getString(PreferencesKey.LOGIN_PASSWORD, "") ?: ""
    )

    override var rememberLogin: Boolean
        get() = sp.getBoolean(PreferencesKey.LOGIN_REMEMBER, false)
        set(value) { sp.edit().putBoolean(PreferencesKey.LOGIN_REMEMBER, value).apply() }

    override var token: String
        get() = securedSp.getString(PreferencesKey.LOGIN_TOKEN, "") ?: ""
        set(value) { securedSp.edit().putString(PreferencesKey.LOGIN_TOKEN, value).apply() }

}

interface SessionManager {
    suspend fun autoConnect(): String
    suspend fun connect(credentials: LoginRequest, save: Boolean): TokenResponse
    fun loadCredentials(): LoginRequest
    var rememberLogin: Boolean
    var token: String
}