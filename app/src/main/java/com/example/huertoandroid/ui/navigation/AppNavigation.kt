package com.example.huertoandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.huertoandroid.ui.screens.home.HomeScreen
import com.example.huertoandroid.ui.screens.productos.ProductsScreen
import com.huertohogar.ui.screens.login.LoginScreen
import com.example.huertoandroid.ui.screens.nosotros.NosotrosScreen
import com.example.huertoandroid.ui.screens.configurar.ConfigurarScreen

@Composable
fun AppNavigation(
    isDarkTheme: Boolean,               // <-- 1. ACEPTA EL ESTADO DEL TEMA
    onThemeChange: (Boolean) -> Unit   // <-- 2. ACEPTA LA FUNCIÓN PARA CAMBIARLO
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route
    ) {
        composable(route = AppScreens.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppScreens.Home.route) {
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = AppScreens.Home.route) {
            HomeScreen(
                onLogout = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                },
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(route = AppScreens.Products.route) {
            ProductsScreen(
                onLogout = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                },
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(route = AppScreens.Nosotros.route) {
            NosotrosScreen(
                onLogout = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                },
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // 3. LA LLAMADA A ConfigurarScreen AHORA ES VÁLIDA
        composable(route = AppScreens.Configurar.route) {
            ConfigurarScreen(
                onLogout = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                },
                onNavigate = { route -> navController.navigate(route) },
                isDarkTheme = isDarkTheme,      // Pasa el estado actual
                onThemeChange = onThemeChange   // Pasa la función para cambiarlo
            )
        }
    }
}

// La sealed class ya está perfecta, no necesita cambios.
sealed class AppScreens(val route: String) {
    object Login: AppScreens("login")
    object Home: AppScreens("home")
    object Products: AppScreens("products")
    object Nosotros : AppScreens("nosotros")
    object Configurar : AppScreens("configurar")
}
