package com.example.proyecto_dsy1105.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.proyecto_dsy1105.ui.screens.*
import com.example.proyecto_dsy1105.viewmodel.AuthViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // Compartir el mismo ViewModel entre las pantallas
    val authViewModel: AuthViewModel = viewModel()
    val estadoAuth by authViewModel.uiState.collectAsState()

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
                },
                irPerfil = {
                    if (estadoAuth.usuario != null) {
                        navController.navigate("perfil")
                    } else {
                        navController.navigate("login")
                    }
                }
            )
        }

        composable("nosotros") {
            NosotrosScreen(
                volver = { navController.popBackStack() }
            )
        }

        composable("programas") {
            ProgramasScreen(
                volver = { navController.popBackStack() },
                irContacto = {
                    navController.navigate("contacto")
                }
            )
        }

        composable("catalogo") {
            CatalogoScreen(
                volver = { navController.popBackStack() },
                irContacto = {
                    navController.navigate("contacto")
                }
            )
        }

        composable("impacto") {
            ImpactoScreen(
                volver = { navController.popBackStack() },
                irColaboraciones = {
                    navController.navigate("colaboraciones")
                }
            )
        }

        composable("colaboraciones") {
            ColaboracionesScreen(
                volver = { navController.popBackStack() },
                irContacto = {
                    navController.navigate("contacto")
                }
            )
        }

        composable("contacto") {
            ContactoScreen(
                volver = { navController.popBackStack() }
            )
        }

        // Inicio de sesión
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                volver = {
                    navController.popBackStack()
                },
                irRegistro = {
                    navController.navigate("registro")
                },
                irPerfil = {
                    navController.navigate("perfil") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Crear una cuenta
        composable("registro") {
            RegistroUsuarioScreen(
                authViewModel = authViewModel,
                volver = {
                    navController.popBackStack()
                },
                irLogin = {
                    navController.navigate("login") {
                        popUpTo("registro") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Perfil con acceso restringido
        composable("perfil") {
            if (estadoAuth.usuario != null) {

                PerfilScreen(
                    volver = {
                        navController.popBackStack()
                    }
                )

            } else {

                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo("perfil") {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}
