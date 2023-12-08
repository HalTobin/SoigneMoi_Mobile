package com.example.soignemoi.feature.login.presentation

sealed class LoginEvent {
    data class ChangeField(val field: LoginField, val text: String): LoginEvent()
    data class ChangeSavedLogin(val state: Boolean): LoginEvent()
    object InvertShowPassword: LoginEvent()
    object Connect: LoginEvent()
}

enum class LoginField { USERNAME, PASSWORD }