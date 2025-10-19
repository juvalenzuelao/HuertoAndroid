package com.example.huertoandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.huertoandroid.ui.navigation.AppNavigation
import com.example.huertoandroid.ui.theme.HuertoAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // controla el tema; persiste en rotaciones
            var isDarkThemeOverride by rememberSaveable { mutableStateOf<Boolean?>(null) }
            val useDarkTheme = isDarkThemeOverride ?: isSystemInDarkTheme()

            HuertoAndroidTheme(darkTheme = useDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        isDarkTheme = useDarkTheme,
                        onThemeChange = { newTheme -> isDarkThemeOverride = newTheme }
                    )
                }
            }
        }
    }
}
