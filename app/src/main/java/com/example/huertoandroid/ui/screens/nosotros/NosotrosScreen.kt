package com.example.huertoandroid.ui.screens.nosotros

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.huertoandroid.R
import com.example.huertoandroid.ui.components.AppBottomBar
import com.example.huertoandroid.ui.components.AppTopBar
import com.example.huertoandroid.ui.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NosotrosScreen(
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf(2) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Sobre Nosotros",
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nuestra Historia",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "HuertoHogar nació hace 6 años con la misión de conectar el campo chileno directamente con tu mesa. Somos un puente entre productores locales y tú, eliminando intermediarios para garantizar frescura y calidad inigualables.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Creemos en un comercio justo y sostenible. Al elegirnos, apoyas a los agricultores de nuestro país y promueves prácticas agrícolas que respetan nuestro medio ambiente. Gracias por ser parte de esta comunidad.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Nuestras Sucursales",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Nuestra sucursal central y huerto principal se encuentra en Paine. Además, puedes encontrarnos en Santiago, Puerto Montt, Villarrica, Viña del Mar, Valparaíso y Concepción.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.mapa_villarica),
                        contentDescription = "Mapa de ubicación de sucursal",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
