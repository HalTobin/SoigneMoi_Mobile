package com.example.soignemoi.feature.login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soignemoi.data.network.LoginRequest
import com.example.soignemoi.data.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val id = authService.loadCredentials()
        _state.update { it.copy(
            saveLogin = authService.rememberLogin,
            username = id.mail,
            password = id.password
        ) }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.ChangeField -> {
                when (event.field) {
                    LoginField.USERNAME -> _state.update { it.copy(username = event.text) }
                    LoginField.PASSWORD -> _state.update { it.copy(password = event.text) }
                }
            }
            is LoginEvent.ChangeSavedLogin -> {
                authService.rememberLogin = event.state
                _state.update { it.copy(saveLogin = authService.rememberLogin) }
            }
            is LoginEvent.InvertShowPassword -> _state.update { it.copy(showPassword = !_state.value.showPassword) }
            is LoginEvent.Connect -> viewModelScope.launch {
                _state.update { it.copy(error = false) }
                try {
                    val response = authService.connect(LoginRequest(state.value.username, state.value.password), _state.value.saveLogin)
                    if (response.role != "Doctor") _state.update { it.copy(error = true) }
                    else _eventFlow.emit(UiEvent.ConnectionSuccess)
                    Log.i("CONNECTION", "Role: ${response.role} Token: ${response.accessToken}")
                } catch (exception: IllegalStateException) {
                    _state.update { it.copy(error = true) }
                }
            }
        }
    }

    sealed class UiEvent {
        object ConnectionSuccess: UiEvent()
    }

}