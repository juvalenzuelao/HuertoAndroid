package com.example.huertoandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// importar las pantallas
import com.example.huertoandroid.ui.screens.login.LoginScreen
import com.example.huertoandroid.ui.screens.home.HomeScreen
import com.example.huertoandroid.ui.screens.productos.ProductsScreen
import com.example.huertoandroid.ui.screens.nosotros.NosotrosScreen
import com.example.huertoandroid.ui.screens.configurar.ConfigurarScreen
import com.example.huertoandroid.ui.register.RegisterScreen

@Composable
fun AppNavigation(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route
    ) {
        // LOGIN
        composable(AppScreens.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppScreens.Home.route) {
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {                    // ← NUEVO: ir a Registro
                    navController.navigate(AppScreens.Register.route)
                }
            )
        }

        // REGISTER (NUEVO DESTINO)
        composable(AppScreens.Register.route) {
            RegisterScreen(
                onRegistered = {
                    // Al registrar, vamos a Home y limpiamos Login/Register del back stack
                    navController.navigate(AppScreens.Home.route) {
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // HOME
        composable(AppScreens.Home.route) {
            HomeScreen(
                onLogout = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                },
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        // PRODUCTOS
        composable(AppScreens.Products.route) {
            ProductsScreen(
                onLogout = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                },
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        // NOSOTROS
        composable(AppScreens.Nosotros.route) {
            NosotrosScreen(
                onLogout = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                },
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        // CONFIGURAR
        composable(AppScreens.Configurar.route) {
            ConfigurarScreen(
                onLogout = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                },
                onNavigate = { route -> navController.navigate(route) },
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }
    }
}

// RUTAS
sealed class AppScreens(val route: String) {
    object Login : AppScreens("login")
    object Register : AppScreens("register")   // ← NUEVA
    object Home : AppScreens("home")
    object Products : AppScreens("products")
    object Nosotros : AppScreens("nosotros")
    object Configurar : AppScreens("configurar")
}
