package com.example.proyecto_dsy1105.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.proyecto_dsy1105.ui.screens.HomeScreen
import com.example.proyecto_dsy1105.ui.screens.NosotrosScreen
import com.example.proyecto_dsy1105.ui.screens.ProgramasScreen
import com.example.proyecto_dsy1105.ui.screens.CatalogoScreen
import com.example.proyecto_dsy1105.ui.screens.ImpactoScreen
import com.example.proyecto_dsy1105.ui.screens.ColaboracionesScreen
import com.example.proyecto_dsy1105.ui.screens.ContactoScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            HomeScreen(
                irNosotros = {
                    navController.navigate("nosotros")
                },
                irProgramas = {
                    navController.navigate("programas")
                },
                irCatalogo = {
                    navController.navigate("catalogo")
                },
                irImpacto = {
                    navController.navigate("impacto")
                },
                irColaboraciones = {
                    navController.navigate("colaboraciones")
                },
                irContacto = {
                    navController.navigate("contacto")
                }
            )
        }

        composable("nosotros") {
            NosotrosScreen(
                volver = {
                    navController.popBackStack()
                }
            )
        }

        composable("programas") {
            ProgramasScreen(
                volver = {
                    navController.popBackStack()
                },
                irContacto = {
                    navController.navigate("contacto")
                }
            )
        }

        composable("catalogo") {
            CatalogoScreen(
                volver = {
                    navController.popBackStack()
                },
                irContacto = {
                    navController.navigate("contacto")
                }
            )
        }

        composable("impacto") {
            ImpactoScreen(
                volver = {
                    navController.popBackStack()
                },
                irColaboraciones = {
                    navController.navigate("colaboraciones")
                }
            )
        }

        composable("colaboraciones") {
            ColaboracionesScreen(
                volver = {
                    navController.popBackStack()
                },
                irContacto = {
                    navController.navigate("contacto")
                }
            )
        }

        composable("contacto") {
            ContactoScreen(
                volver = {
                    navController.popBackStack()
                }
            )
        }
    }
}
