package com.example.soignemoi.data.service

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.soignemoi.data.network.LoginRequest
import com.example.soignemoi.data.network.TokenResponse
import com.example.soignemoi.data.repository.AuthRepository
import com.example.soignemoi.util.PreferencesKey
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val sp: SharedPreferences,
    private val securedSp: SharedPreferences
): AuthService {
    override suspend fun connect(credentials: LoginRequest, save: Boolean): TokenResponse {
        saveCredentials(credentials)
        try {
            val response = authRepository.connect(credentials)
            securedSp.edit().putString(PreferencesKey.LOGIN_TOKEN, response.accessToken).apply()
            return response
        } catch (exception: IllegalStateException) {
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
}

interface AuthService {
    suspend fun connect(credentials: LoginRequest, save: Boolean): TokenResponse
    fun loadCredentials(): LoginRequest

    var rememberLogin: Boolean
}