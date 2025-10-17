package com.example.huertoandroid.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


private data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

@Composable
fun AppBottomBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val navItems = listOf(
        BottomNavItem("Inicio", Icons.Default.Home),
        BottomNavItem("Productos", Icons.Default.ShoppingCart),
        BottomNavItem("Quiénes Somos", Icons.Default.Info),
        BottomNavItem("Configurar", Icons.Default.Settings)
    )

    NavigationBar(modifier = modifier) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}
