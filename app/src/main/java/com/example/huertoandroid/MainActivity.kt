package com.example.huertoandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.huertoandroid.ui.navigation.AppNavigation
import com.example.huertoandroid.ui.theme.HuertoAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. Estado para controlar si el tema oscuro está activo
            // Por defecto, usa la configuración del sistema.
            var isDarkTheme by remember { mutableStateOf<Boolean?>(null) }
            val useDarkTheme = isDarkTheme ?: isSystemInDarkTheme()

            // 2. Envuelve toda la app en el tema, pasando el estado
            HuertoAndroidTheme(darkTheme = useDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 3. Pasa el estado y la función para cambiarlo a AppNavigation
                    AppNavigation(
                        isDarkTheme = useDarkTheme,
                        onThemeChange = { newThemeState -> isDarkTheme = newThemeState }
                    )
                }
            }
        }
    }
}
