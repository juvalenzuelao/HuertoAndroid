package com.example.huertoandroid.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        // Texto de ayuda que se muestra cuando la barra está vacía
        placeholder = { Text("Buscar productos...") },
        // Icono de la lupa al principio
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Icono de búsqueda"
            )
        },
        // Hace que la barra tenga una sola línea de alto
        singleLine = true,
        // Define la forma de la barra (esquinas redondeadas)
        shape = RoundedCornerShape(24.dp),
        // Personaliza los colores para que no tenga un borde tan pronunciado
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent, // Sin línea de color al hacer foco
            unfocusedIndicatorColor = Color.Transparent, // Sin línea de color al perder foco
            disabledIndicatorColor = Color.Transparent,
        )
    )
}
