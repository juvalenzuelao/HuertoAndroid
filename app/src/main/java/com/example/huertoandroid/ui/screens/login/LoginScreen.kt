package com.example.huertoandroid.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    // Variables que guardan lo que escribe el usuario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Entrar
        Button(
            onClick = {
                // Validación simple con if
                if (email.isBlank()) {
                    Toast.makeText(context, "Escribe tu email", Toast.LENGTH_SHORT).show()
                } else if (!email.contains("@")) {
                    Toast.makeText(context, "El email debe contener @", Toast.LENGTH_SHORT).show()
                } else if (!email.contains(".")) {
                    Toast.makeText(context, "El email debe contener un dominio válido (ej: .com)", Toast.LENGTH_SHORT).show()
                } else if (password.isBlank()) {
                    Toast.makeText(context, "Escribe tu contraseña", Toast.LENGTH_SHORT).show()
                } else if (password.length < 6) {
                    Toast.makeText(context, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                } else {
                    // Todo está bien, iniciar sesión
                    Toast.makeText(context, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para ir al Registro
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}