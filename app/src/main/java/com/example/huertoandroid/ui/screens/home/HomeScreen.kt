package com.example.huertoandroid.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.huertoandroid.R
import com.example.huertoandroid.ui.components.AppBottomBar
import com.example.huertoandroid.ui.components.AppTopBar
import com.example.huertoandroid.ui.navigation.AppScreens
import androidx.compose.ui.platform.LocalContext
import com.example.huertoandroid.model.WeatherResponse
import com.example.huertoandroid.network.RetrofitClient
import kotlinx.coroutines.launch
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit
) {
    // El estado para el ítem seleccionado del BottomBar
    var selectedItem by remember { mutableStateOf(0) }
    // Estado para el clima
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var weatherData by remember { mutableStateOf<WeatherResponse?>(null) }
    var isLoadingWeather by remember { mutableStateOf(true) }

    // Cargar el clima cuando se muestra la pantalla
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getWeather()
                if (response.isSuccessful) {
                    weatherData = response.body()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar clima", Toast.LENGTH_SHORT).show()
            } finally {
                isLoadingWeather = false
            }
        }
    }

    Scaffold(
        // 2. Llama a TopBar, pasándole el título y la acción de logout
        topBar = {
            AppTopBar(
                title = "Bienvenido a HuertoHogar",
                onLogout = onLogout
            )
        },
        // 3. Llama a BottomBar, pasándole el estado y la función para actualizarlo
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
    ) { padding ->
        // El contenido principal de la pantalla (Column, Cards, etc.) no ha cambiado.
        // Ahora está mucho más claro qué es "estructura" (Scaffold) y qué es "contenido".
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Card del Clima
            if (!isLoadingWeather && weatherData != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "🌤️ ${weatherData!!.name}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = weatherData!!.weather[0].description.capitalize(),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        Text(
                            text = "${weatherData!!.main.temp.toInt()}°C",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // Banner Hero
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Del Campo a Tu Mesa",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "6 años conectando nuestros productos con tu hogar",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Título de sección
            Text(
                text = "Productos Destacados",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Grid de productos (2 columnas)
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                // Fila 1: Plátano y Naranja
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProductCard(
                        title = "Plátano Cavendish",
                        imageRes = R.drawable.platano_cavendish,
                        modifier = Modifier.weight(1f),
                        price = "2500 x kg"
                    )
                    ProductCard(
                        title = "Naranja Valencia",
                        imageRes = R.drawable.naranja_valencia,
                        modifier = Modifier.weight(1f),
                        price = "3500 x kg"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Fila 2: Miel y Manzanas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProductCard(
                        title = "Miel",
                        imageRes = R.drawable.miel_panal,
                        modifier = Modifier.weight(1f),
                        price = "8000 x litro"
                    )
                    ProductCard(
                        title = "Manzanas",
                        imageRes = R.drawable.manzanas,
                        modifier = Modifier.weight(1f),
                        price = "1500 x kg"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ProductCard puede quedarse aquí, o moverse a `components` si se usa en más pantallas.
// Dado que `ProductsScreen` también lo necesitará, moverlo a `components` sería el siguiente paso lógico.
@Composable
fun ProductCard(
    title: String,
    price: String,
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.aspectRatio(1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = price,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
