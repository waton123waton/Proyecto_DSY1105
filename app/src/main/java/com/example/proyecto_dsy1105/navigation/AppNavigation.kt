package com.example.proyecto_dsy1105.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_dsy1105.ui.screens.CitasScreen
import com.example.proyecto_dsy1105.ui.screens.HomeScreen
import com.example.proyecto_dsy1105.ui.screens.MascotasScreen
import com.example.proyecto_dsy1105.ui.screens.RegistroMascotaScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                irMascotas = {
                    navController.navigate("mascotas")
                },
                irCitas = {
                    navController.navigate("citas")
                }
            )
        }

        composable("mascotas") {
            MascotasScreen(
                registrarMascota = {
                    navController.navigate("registroMascota")
                },
                volver = {
                    navController.popBackStack()
                }
            )
        }

        composable("registroMascota") {
            RegistroMascotaScreen(
                volver = {
                    navController.popBackStack()
                }
            )
        }

        composable("citas") {
            CitasScreen(
                volver = {
                    navController.popBackStack()
                }
            )
        }
    }
}