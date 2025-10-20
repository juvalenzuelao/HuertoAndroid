package com.example.huertoandroid.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val handleLoginClick = {
        when {
            email.isBlank() -> Toast.makeText(context, "Escribe tu email", Toast.LENGTH_SHORT).show()
            !email.contains("@") -> Toast.makeText(context, "El email debe contener @", Toast.LENGTH_SHORT).show()
            !email.contains(".") -> Toast.makeText(context, "El email debe contener un dominio válido (ej: .com)", Toast.LENGTH_SHORT).show()
            password.isBlank() -> Toast.makeText(context, "Escribe tu contraseña", Toast.LENGTH_SHORT).show()
            password.length < 6 -> Toast.makeText(context, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            else -> {
                Toast.makeText(context, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            }
        }
    }

    LoginContent(
        email = email,
        password = password,            
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLoginClick = handleLoginClick,
        onNavigateToRegister = onNavigateToRegister,
        modifier = Modifier.fillMaxSize()
    )
}
