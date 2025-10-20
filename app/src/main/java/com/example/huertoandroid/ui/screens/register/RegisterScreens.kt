package com.example.huertoandroid.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.huertoandroid.data.database.User
import com.example.huertoandroid.data.database.UserDatabase
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    // Variables que guardan lo que escribe el usuario
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope() // Para operaciones de base de datos
    val database = UserDatabase.getDatabase(context)

    // Pantalla con scroll
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Repetir Contraseña
        OutlinedTextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repetir Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Registrarse
        Button(
            onClick = {
                // Validaciones
                when {
                    nombre.isBlank() -> {
                        Toast.makeText(context, "Escribe tu nombre", Toast.LENGTH_SHORT).show()
                    }
                    email.isBlank() -> {
                        Toast.makeText(context, "Escribe tu email", Toast.LENGTH_SHORT).show()
                    }
                    !email.contains("@") -> {
                        Toast.makeText(context, "El email debe contener @", Toast.LENGTH_SHORT).show()
                    }
                    password.length < 6 -> {
                        Toast.makeText(context, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                    }
                    password != repeatPassword -> {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // Guardar en base de datos
                        scope.launch {
                            try {
                                // Verificar si el email ya existe
                                val existingUser = database.userDao().getUserByEmail(email)
                                if (existingUser != null) {
                                    Toast.makeText(context, "Este email ya está registrado", Toast.LENGTH_SHORT).show()
                                } else {
                                    // Crear nuevo usuario y guardar
                                    val newUser = User(
                                        nombre = nombre,
                                        email = email,
                                        password = password
                                    )
                                    database.userDao().insertUser(newUser)
                                    Toast.makeText(context, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                                    onRegisterSuccess()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para ir al Login
        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}