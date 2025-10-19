package com.example.huertoandroid.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertoandroid.data.repo.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterFormState(
    val nombre: String = "",
    val email: String = "",
    val pass: String = "",
    val pass2: String = "",
    val errNombre: String? = null,
    val errEmail: String? = null,
    val errPass: String? = null,
    val errPass2: String? = null,
    val isSubmitting: Boolean = false
) {
    val isValid get() =
        errNombre == null && errEmail == null && errPass == null && errPass2 == null &&
                nombre.isNotBlank() && email.isNotBlank() && pass.isNotBlank() && pass2.isNotBlank()
}

sealed interface RegisterEvent {
    data object Registered : RegisterEvent
    data class Message(val msg: String) : RegisterEvent
}

class RegisterViewModel(private val repo: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(RegisterFormState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<RegisterEvent>()
    val events = _events.asSharedFlow()

    fun onNombre(v: String) = _state.update {
        val x = v.trimStart()
        it.copy(nombre = x, errNombre = if (x.isBlank()) "Requerido" else null)
    }

    fun onEmail(v: String) = _state.update {
        val x = v.trim()
        it.copy(email = x, errEmail = if (!Patterns.EMAIL_ADDRESS.matcher(x).matches()) "Email inválido" else null)
    }

    fun onPass(v: String) = _state.update {
        it.copy(
            pass = v,
            errPass = when {
                v.length < 8 -> "Mínimo 8 caracteres"
                !v.any(Char::isDigit) -> "Incluye un número"
                !v.any(Char::isUpperCase) -> "Incluye una mayúscula"
                else -> null
            },
            errPass2 = if (it.pass2.isNotBlank() && it.pass2 != v) "No coincide" else null
        )
    }

    fun onPass2(v: String) = _state.update {
        it.copy(pass2 = v, errPass2 = if (v != it.pass) "No coincide" else null)
    }

    fun submit() = viewModelScope.launch {
        val s = state.value
        if (!s.isValid || s.isSubmitting) return@launch

        _state.update { it.copy(isSubmitting = true) }
        try {
            // email único
            if (!repo.isEmailAvailable(s.email)) {
                _state.update { it.copy(isSubmitting = false) }
                _events.emit(RegisterEvent.Message("Ese email ya está registrado"))
                return@launch
            }
            // registrar
            repo.register(s.nombre.trim(), s.email.trim(), s.pass)
            _state.value = RegisterFormState()   // limpia el formulario
            _events.emit(RegisterEvent.Registered)
        } catch (e: Exception) {
            _state.update { it.copy(isSubmitting = false) }
            _events.emit(RegisterEvent.Message("No se pudo registrar. Intenta de nuevo."))
        }
    }
}
