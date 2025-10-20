package com.example.huertoandroid.ui.screens.productos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.huertoandroid.R
import com.example.huertoandroid.ui.components.*
import com.example.huertoandroid.ui.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit
) {
    // 1. Añadimos el estado para el BottomBar. El índice 1 corresponde a "Productos".
    var selectedItem by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Nuestros Productos",
                onLogout = onLogout
            )
        },
        bottomBar = {
            // 2. Llamamos a AppBottomBar con sus parámetros
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
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item { SearchBar(modifier = Modifier.fillMaxWidth()) }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                ProductRow(
                    product1 = ProductInfo("Espinacas Frescas", "$ 1.800 / manojo", R.drawable.espinacas),
                    product2 = ProductInfo("Manzana Fuji", "$ 1.500 / kg", R.drawable.manzanas)
                )
            }

            item {
                ProductRow(
                    product1 = ProductInfo("Miel de Romero", "$ 7.500 / kg", R.drawable.miel_panal),
                    product2 = ProductInfo("Naranja Valencia", "$ 1.200 / kg", R.drawable.naranja_valencia)
                )
            }

            item {
                ProductRow(
                    product1 = ProductInfo("Pimiento Rojo", "$ 2.100 / kg", R.drawable.pimientos),
                    product2 = ProductInfo("Plátano Cavendish", "$ 1.300 / kg", R.drawable.platano_cavendish)
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
