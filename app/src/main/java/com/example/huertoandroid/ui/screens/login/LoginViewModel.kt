package com.example.huertoandroid.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertoandroid.data.repo.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface LoginEvent {
    data object Success : LoginEvent
    data class Message(val msg: String) : LoginEvent
}

class LoginViewModel(private val repo: UserRepository) : ViewModel() {
    private val _events = MutableSharedFlow<LoginEvent>()
    val events = _events.asSharedFlow()

    fun login(email: String, pass: String) = viewModelScope.launch {
        when {
            email.isBlank() -> { _events.emit(LoginEvent.Message("Ingresa tu correo")); return@launch }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> { _events.emit(LoginEvent.Message("Correo inválido")); return@launch }
            pass.isBlank() -> { _events.emit(LoginEvent.Message("Ingresa tu contraseña")); return@launch }
        }
        val ok = try { repo.login(email.trim(), pass) } catch (_: Exception) { false }
        if (ok) _events.emit(LoginEvent.Success) else _events.emit(LoginEvent.Message("Credenciales incorrectas"))
    }
}
