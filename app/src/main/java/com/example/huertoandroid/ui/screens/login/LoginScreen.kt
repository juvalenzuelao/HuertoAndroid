package com.example.huertoandroid.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertoandroid.ui.theme.HuertoGreen
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    // VM con Room (factory)
    val vm: LoginViewModel = viewModel(
        factory = LoginVmFactory(LocalContext.current.applicationContext)
    )
    val host = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // escuchar eventos del VM
    LaunchedEffect(Unit) {
        vm.events.collect { ev ->
            when (ev) {
                is LoginEvent.Success -> onLoginSuccess()
                is LoginEvent.Message -> scope.launch { host.showSnackbar(ev.msg) }
            }
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val canLogin = email.isNotBlank() && password.isNotBlank()

    Scaffold(snackbarHost = { SnackbarHost(host) }) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(inner),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // “logo”
            Text("HuertoHogar", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = HuertoGreen)
            Text(
                text = "Del campo a tu hogar",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                singleLine = true
            )

            // password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, null)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                singleLine = true
            )

            TextButton(
                onClick = { /* TODO: recuperar */ },
                modifier = Modifier.align(Alignment.End).padding(bottom = 24.dp)
            ) { Text("¿Olvidaste tu contraseña?") }

            // login real (usa Room vía VM)
            Button(
                onClick = { vm.login(email, password) },
                enabled = canLogin,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = HuertoGreen)
            ) { Text("Iniciar Sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold) }

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) { Text("Crear cuenta nueva") }
        }
    }
}
