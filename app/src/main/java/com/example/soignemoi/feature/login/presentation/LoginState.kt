package com.example.soignemoi.feature.login.presentation

data class LoginState(
    val username: String = "",
    val password: String = "",
    val saveLogin: Boolean = false,
    val showPassword: Boolean = false,
    val error: Boolean = false
)