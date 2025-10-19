package com.example.huertoandroid.ui.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegistered: () -> Unit,
    // ✅ agregado: VM con factory (Room)
    vm: RegisterViewModel = viewModel(
        factory = RegisterVmFactory(LocalContext.current.applicationContext)
    )
) {
    val s by vm.state.collectAsState()
    val host = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // ✅ agregado: escuchar eventos (éxito / mensajes)
    LaunchedEffect(Unit) {
        vm.events.collect { ev ->
            when (ev) {
                is RegisterEvent.Registered -> {
                    scope.launch { host.showSnackbar("¡Cuenta creada!") }
                    onRegistered()
                }
                is RegisterEvent.Message -> {
                    scope.launch { host.showSnackbar(ev.msg) }
                }
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(host) }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = s.nombre,
                onValueChange = vm::onNombre,
                label = { Text("Nombre") },
                isError = s.errNombre != null,
                supportingText = { s.errNombre?.let { Text(it) } },
                trailingIcon = { if (s.errNombre != null) Icon(Icons.Default.Error, null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = s.email,
                onValueChange = vm::onEmail,
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = s.errEmail != null,
                supportingText = { s.errEmail?.let { Text(it) } },
                trailingIcon = { if (s.errEmail != null) Icon(Icons.Default.Error, null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = s.pass,
                onValueChange = vm::onPass,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = s.errPass != null,
                supportingText = { s.errPass?.let { Text(it) } },
                trailingIcon = { if (s.errPass != null) Icon(Icons.Default.Error, null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = s.pass2,
                onValueChange = vm::onPass2,
                label = { Text("Repite contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = s.errPass2 != null,
                supportingText = { s.errPass2?.let { Text(it) } },
                trailingIcon = { if (s.errPass2 != null) Icon(Icons.Default.Error, null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(visible = !s.isValid) {
                Text(
                    "Completa los campos correctamente para continuar",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = vm::submit,
                enabled = s.isValid && !s.isSubmitting,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                if (s.isSubmitting)
                    CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(22.dp))
                else
                    Text("Crear cuenta")
            }
        }
    }
}
