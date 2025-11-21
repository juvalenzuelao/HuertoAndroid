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
import com.example.huertoandroid.model.Product
import com.example.huertoandroid.network.RetrofitClient
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
    var selectedItem by remember { mutableStateOf(4) }

    // Lista de productos
    var productList by remember { mutableStateOf<List<Product>>(emptyList()) }

    // Campo de búsqueda
    var searchText by remember { mutableStateOf("") }

    // Dropdown de categorías para filtrar
    var categoriaSeleccionada by remember { mutableStateOf("") }
    var expandedDropdown by remember { mutableStateOf(false) }
    val categorias = listOf("Verduras", "Frutas", "Otros")

    // Formulario (crear o editar)
    var productoId by remember { mutableStateOf<Long?>(null) }
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidadKg by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var expandedFormDropdown by remember { mutableStateOf(false) }
    var modoEdicion by remember { mutableStateOf(false) }

    // Función para limpiar formulario
    fun limpiarFormulario() {
        productoId = null
        nombre = ""
        descripcion = ""
        precio = ""
        cantidadKg = ""
        categoria = ""
        modoEdicion = false
    }

    // Función para cargar producto en formulario (editar)
    fun cargarProducto(product: Product) {
        productoId = product.id
        nombre = product.nombre
        descripcion = product.descripcion
        precio = product.precio.toString()
        cantidadKg = product.cantidadKg.toString()
        categoria = product.categoria
        modoEdicion = true
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Gestión de Productos",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ========== SECCIÓN 1: BÚSQUEDA Y FILTROS ==========
            item {
                Text(
                    text = "Buscar y Filtrar Productos",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Búsqueda por nombre
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("Buscar por nombre") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val response =
                                        RetrofitClient.apiService.searchProducts(searchText)
                                    if (response.isSuccessful) {
                                        productList = response.body() ?: emptyList()
                                        Toast.makeText(
                                            context,
                                            "Búsqueda completada",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Error: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    ) {
                        Text("Buscar")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Filtros: Categoría y Stock Bajo
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Dropdown de categorías
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { expandedDropdown = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (categoriaSeleccionada.isEmpty()) "Filtrar por Categoría" else categoriaSeleccionada
                            )
                        }

                        DropdownMenu(
                            expanded = expandedDropdown,
                            onDismissRequest = { expandedDropdown = false }
                        ) {
                            categorias.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        categoriaSeleccionada = cat
                                        expandedDropdown = false
                                        scope.launch {
                                            try {
                                                val response =
                                                    RetrofitClient.apiService.getProductsByCategory(
                                                        cat
                                                    )
                                                if (response.isSuccessful) {
                                                    productList = response.body() ?: emptyList()
                                                    Toast.makeText(
                                                        context,
                                                        "Filtrado por $cat",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            } catch (e: Exception) {
                                                Toast.makeText(
                                                    context,
                                                    "Error: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }

                    // Botón productos con stock bajo
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val response = RetrofitClient.apiService.getAllProducts()
                                    if (response.isSuccessful) {
                                        val allProducts = response.body() ?: emptyList()
                                        productList = allProducts.filter { it.cantidadKg <= 5 }
                                        Toast.makeText(
                                            context,
                                            "Productos con stock bajo (≤5 Kg)",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Error: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Stock Bajo")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ========== SECCIÓN 2: FORMULARIO CREAR/EDITAR ==========
            item {
                Text(
                    text = if (modoEdicion) "Editar Producto" else "Crear Producto",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del producto") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = { Text("Precio (CLP)") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = cantidadKg,
                        onValueChange = { cantidadKg = it },
                        label = { Text("Stock (Kg)") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Dropdown para categoría en formulario
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = categoria,
                        onValueChange = { },
                        label = { Text("Categoría") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            TextButton(onClick = { expandedFormDropdown = true }) {
                                Text("▼")
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expandedFormDropdown,
                        onDismissRequest = { expandedFormDropdown = false }
                    ) {
                        categorias.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    categoria = cat
                                    expandedFormDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (modoEdicion) {
                        Button(
                            onClick = { limpiarFormulario() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val product = Product(
                                        id = productoId,
                                        nombre = nombre,
                                        descripcion = descripcion,
                                        precio = precio.toDouble(),
                                        cantidadKg = cantidadKg.toInt(),
                                        categoria = categoria,
                                        imagenUrl = null
                                    )

                                    val response = if (modoEdicion) {
                                        RetrofitClient.apiService.updateProduct(
                                            productoId!!,
                                            product
                                        )
                                    } else {
                                        RetrofitClient.apiService.createProduct(product)
                                    }

                                    if (response.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            if (modoEdicion) "Producto actualizado" else "Producto creado",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        limpiarFormulario()
                                        // Recargar lista
                                        val listResponse =
                                            RetrofitClient.apiService.getAllProducts()
                                        if (listResponse.isSuccessful) {
                                            productList = listResponse.body() ?: emptyList()
                                        }
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Error: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (modoEdicion) "Actualizar" else "Crear")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ========== SECCIÓN 3: LISTA DE PRODUCTOS ==========
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Lista de Productos (${productList.size})",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val response = RetrofitClient.apiService.getAllProducts()
                                    if (response.isSuccessful) {
                                        productList = response.body() ?: emptyList()
                                        categoriaSeleccionada = ""
                                        Toast.makeText(
                                            context,
                                            "Lista actualizada",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Error: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    ) {
                        Text("Recargar Todo")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Lista de productos
            if (productList.isEmpty()) {
                item {
                    Text("No hay productos. Presiona 'Recargar Todo' o usa filtros")
                }
            } else {
                items(productList) { product ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Text(
                                text = product.nombre,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = product.descripcion,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = " Precio: $${product.precio} CLP | Stock: ${product.cantidadKg} Kg",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = " Categoría: ${product.categoria}",
                                style = MaterialTheme.typography.bodySmall,
                                color = when (product.categoria) {
                                    "Verduras" -> MaterialTheme.colorScheme.primary
                                    "Frutas" -> MaterialTheme.colorScheme.secondary
                                    else -> MaterialTheme.colorScheme.tertiary
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { cargarProducto(product) },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("✏️ Editar")
                                }

                                Button(
                                    onClick = {
                                        scope.launch {
                                            try {
                                                val response =
                                                    RetrofitClient.apiService.deleteProduct(product.id!!)
                                                if (response.isSuccessful) {
                                                    Toast.makeText(
                                                        context,
                                                        "Eliminado",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    val listResponse =
                                                        RetrofitClient.apiService.getAllProducts()
                                                    if (listResponse.isSuccessful) {
                                                        productList =
                                                            listResponse.body() ?: emptyList()
                                                    }
                                                }
                                            } catch (e: Exception) {
                                                Toast.makeText(
                                                    context,
                                                    "Error: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(" Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
