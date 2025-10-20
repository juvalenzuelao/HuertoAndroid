package com.example.huertoandroid.ui.screens.admin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.huertoandroid.data.database.User
import com.example.huertoandroid.data.database.UserDatabase
import com.example.huertoandroid.ui.components.AppBottomBar
import com.example.huertoandroid.ui.components.AppTopBar
import com.example.huertoandroid.ui.navigation.AppScreens
import kotlinx.coroutines.launch

@Composable
fun AdminScreen(
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = UserDatabase.getDatabase(context)

    // Estado para el BottomBar
    var selectedItem by remember { mutableStateOf(4) }

    // Lista de usuarios
    var userList by remember { mutableStateOf<List<User>>(emptyList()) }
    var mostrarLista by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Panel de Administrador",
                onLogout = onLogout
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedItem = selectedItem,
                onItemSelected = { newIndex ->
                    selectedItem = newIndex
                    when (newIndex) {
                        0 -> onNavigate(AppScreens.Home.route)
                        1 -> onNavigate(AppScreens.Products.route)
                        2 -> onNavigate(AppScreens.Nosotros.route)
                        3 -> onNavigate(AppScreens.Configurar.route)
                        4 -> onNavigate(AppScreens.Admin.route)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Usuarios Registrados",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para listar usuarios
            Button(
                onClick = {
                    scope.launch {
                        try {
                            userList = database.userDao().getAllUsers()
                            mostrarLista = true
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Listar Usuarios")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar la lista o mensaje
            if (!mostrarLista) {
                Text(
                    text = "Presiona el botón para ver los usuarios",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else if (userList.isEmpty()) {
                Text(
                    text = "No hay usuarios registrados",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(userList) { user ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Nombre: ${user.nombre}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Email: ${user.email}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}