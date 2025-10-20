package com.example.huertoandroid.ui.screens.configurar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.huertoandroid.ui.components.AppBottomBar
import com.example.huertoandroid.ui.components.AppTopBar
import com.example.huertoandroid.ui.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurarScreen(
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    var selectedItem by remember { mutableStateOf(3) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Configuración",
                onLogout = onLogout
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedItem = selectedItem,
                onItemSelected = { newIndex ->
                    if (newIndex != selectedItem) {
                        when (newIndex) {
                            0 -> onNavigate(AppScreens.Home.route)
                            1 -> onNavigate(AppScreens.Products.route)
                            2 -> onNavigate(AppScreens.Nosotros.route)
                            3 -> onNavigate(AppScreens.Configurar.route)
                            4 -> onNavigate(AppScreens.Admin.route)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Sección de Perfil
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Icono de perfil",
                    modifier = Modifier.size(96.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Martín", // Puedes reemplazar esto con un nombre de usuario real
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            // --- Opción de Modo Oscuro ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.NightsStay,
                    contentDescription = "Icono de modo oscuro",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Modo Oscuro",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { isChecked ->
                        onThemeChange(isChecked)
                    }
                )
            }
            Divider() // Línea divisoria

            // --- Opción de Cambiar Contraseña ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cambiar contraseña",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Ir a cambiar contraseña"
                )
            }
            Divider()
        }
    }
}
