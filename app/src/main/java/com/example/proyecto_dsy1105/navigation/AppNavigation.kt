
package com.example.proyecto_dsy1105.navigation

import android.content.pm.ApplicationInfo
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.proyecto_dsy1105.model.RolUsuario
import com.example.proyecto_dsy1105.ui.screens.*
import com.example.proyecto_dsy1105.ui.theme.*
import com.example.proyecto_dsy1105.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

private data class OpcionMenu(
    val ruta: String,
    val nombre: String,
    val icono: ImageVector
)

private val opcionesMenu = listOf(
    OpcionMenu("inicio", "Inicio", Icons.Default.Home),
    OpcionMenu("nosotros", "Quiénes somos", Icons.Default.Info),
    OpcionMenu("programas", "Programas", Icons.Default.School),
    OpcionMenu("catalogo", "Catálogo", Icons.Default.ShoppingBag),
    OpcionMenu("impacto", "Impacto social", Icons.Default.Favorite),
    OpcionMenu(
        "colaboraciones",
        "Colaboraciones",
        Icons.Default.VolunteerActivism
    ),
    OpcionMenu("contacto", "Contacto", Icons.Default.Email),
    OpcionMenu("perfil", "Mi cuenta", Icons.Default.Person)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val esDebug = (
            context.applicationInfo.flags and
                    ApplicationInfo.FLAG_DEBUGGABLE
            ) != 0

    val authViewModel: AuthViewModel = viewModel()
    val estadoAuth by authViewModel.uiState.collectAsState()

    val esAdministrador =
        estadoAuth.usuario?.rol == RolUsuario.ADMINISTRADOR

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route ?: "inicio"

    val mostrarMenus =
        rutaActual != "login" && rutaActual != "registro"

    fun navegar(ruta: String) {

        val destino = when {
            ruta == "perfil" && estadoAuth.usuario == null ->
                "login"

            ruta == "admin" && !esAdministrador ->
                "inicio"

            ruta == "admin-prueba" && !esDebug ->
                "inicio"

            else -> ruta
        }

        navController.navigate(destino) {
            popUpTo(
                navController.graph.findStartDestination().id
            ) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }

    fun cerrarSesion() {
        authViewModel.cerrarSesion()

        navController.navigate("inicio") {
            popUpTo("inicio") {
                inclusive = false
            }
            launchSingleTop = true
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = mostrarMenus,
        drawerContent = {

            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Text(
                        text = "CORPALIV",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = RojoPrincipal
                    )

                    Text(
                        text = "Alba Lab",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NegroPrincipal
                    )
                }

                HorizontalDivider()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    items(opcionesMenu) { opcion ->

                        NavigationDrawerItem(
                            label = {
                                Text(opcion.nombre)
                            },
                            selected = rutaActual == opcion.ruta,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    navegar(opcion.ruta)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = opcion.icono,
                                    contentDescription = null
                                )
                            },
                            colors = coloresDrawer()
                        )
                    }

                    if (esAdministrador) {

                        item {

                            HorizontalDivider(
                                modifier = Modifier.padding(
                                    vertical = 8.dp
                                )
                            )

                            NavigationDrawerItem(
                                label = {
                                    Text("Panel de administración")
                                },
                                selected = rutaActual == "admin",
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                        navegar("admin")
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector =
                                            Icons.Default.AdminPanelSettings,
                                        contentDescription = null
                                    )
                                },
                                colors = coloresDrawer()
                            )
                        }
                    }

                    if (esDebug) {

                        item {

                            HorizontalDivider(
                                modifier = Modifier.padding(
                                    vertical = 8.dp
                                )
                            )

                            NavigationDrawerItem(
                                label = {
                                    Text("Administrador de prueba")
                                },
                                selected = rutaActual == "admin-prueba",
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                        navegar("admin-prueba")
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Build,
                                        contentDescription = null
                                    )
                                },
                                colors = coloresDrawer()
                            )
                        }
                    }

                    if (estadoAuth.usuario != null) {

                        item {

                            HorizontalDivider(
                                modifier = Modifier.padding(
                                    vertical = 8.dp
                                )
                            )

                            NavigationDrawerItem(
                                label = {
                                    Text("Cerrar sesión")
                                },
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                        cerrarSesion()
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Logout,
                                        contentDescription = null
                                    )
                                },
                                colors = coloresDrawer()
                            )
                        }
                    }
                }
            }
        }
    ) {

        Scaffold(
            containerColor = Color.White,

            topBar = {
                if (mostrarMenus) {

                    TopAppBar(
                        title = {
                            Column {

                                Text(
                                    text = "CORPALIV",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = NegroPrincipal
                                )

                                Text(
                                    text = "Alba Lab",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = RojoPrincipal
                                )
                            }
                        },

                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Abrir menú",
                                    tint = NegroPrincipal
                                )
                            }
                        },

                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White
                        )
                    )
                }
            },

            bottomBar = {
                if (mostrarMenus) {

                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 6.dp
                    ) {

                        NavigationBarItem(
                            selected = rutaActual == "inicio",
                            onClick = {
                                navegar("inicio")
                            },
                            icon = {
                                Icon(
                                    Icons.Default.Home,
                                    contentDescription = "Inicio"
                                )
                            },
                            label = {
                                Text("Inicio")
                            },
                            colors = coloresBarra()
                        )

                        NavigationBarItem(
                            selected = rutaActual == "programas",
                            onClick = {
                                navegar("programas")
                            },
                            icon = {
                                Icon(
                                    Icons.Default.School,
                                    contentDescription = "Programas"
                                )
                            },
                            label = {
                                Text("Programas")
                            },
                            colors = coloresBarra()
                        )

                        NavigationBarItem(
                            selected = rutaActual == "catalogo",
                            onClick = {
                                navegar("catalogo")
                            },
                            icon = {
                                Icon(
                                    Icons.Default.ShoppingBag,
                                    contentDescription = "Catálogo"
                                )
                            },
                            label = {
                                Text("Catálogo")
                            },
                            colors = coloresBarra()
                        )

                        NavigationBarItem(
                            selected = rutaActual == "colaboraciones",
                            onClick = {
                                navegar("colaboraciones")
                            },
                            icon = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Colaborar"
                                )
                            },
                            label = {
                                Text("Colaborar")
                            },
                            colors = coloresBarra()
                        )

                        NavigationBarItem(
                            selected = rutaActual == "perfil",
                            onClick = {
                                navegar("perfil")
                            },
                            icon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Mi cuenta"
                                )
                            },
                            label = {
                                Text("Cuenta")
                            },
                            colors = coloresBarra()
                        )
                    }
                }
            }
        ) { paddingInterno ->

            NavHost(
                navController = navController,
                startDestination = "inicio",
                modifier = Modifier.padding(paddingInterno),

                enterTransition = {
                    slideIntoContainer(
                        towards =
                            AnimatedContentTransitionScope
                                .SlideDirection.Left,
                        animationSpec = tween(300)
                    ) + fadeIn(
                        animationSpec = tween(300)
                    )
                },

                exitTransition = {
                    slideOutOfContainer(
                        towards =
                            AnimatedContentTransitionScope
                                .SlideDirection.Left,
                        animationSpec = tween(300)
                    ) + fadeOut(
                        animationSpec = tween(240)
                    )
                },

                popEnterTransition = {
                    slideIntoContainer(
                        towards =
                            AnimatedContentTransitionScope
                                .SlideDirection.Right,
                        animationSpec = tween(300)
                    ) + fadeIn(
                        animationSpec = tween(300)
                    )
                },

                popExitTransition = {
                    slideOutOfContainer(
                        towards =
                            AnimatedContentTransitionScope
                                .SlideDirection.Right,
                        animationSpec = tween(300)
                    ) + fadeOut(
                        animationSpec = tween(240)
                    )
                }
            ) {

                composable("inicio") {
                    HomeScreen(
                        irNosotros = {
                            navegar("nosotros")
                        },
                        irProgramas = {
                            navegar("programas")
                        },
                        irCatalogo = {
                            navegar("catalogo")
                        },
                        irImpacto = {
                            navegar("impacto")
                        },
                        irColaboraciones = {
                            navegar("colaboraciones")
                        },
                        irContacto = {
                            navegar("contacto")
                        },
                        irPerfil = {
                            navegar("perfil")
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
                            navegar("contacto")
                        }
                    )
                }

                composable("catalogo") {
                    CatalogoScreen(
                        volver = {
                            navController.popBackStack()
                        },
                        irContacto = {
                            navegar("contacto")
                        }
                    )
                }

                composable("impacto") {
                    ImpactoScreen(
                        volver = {
                            navController.popBackStack()
                        },
                        irColaboraciones = {
                            navegar("colaboraciones")
                        }
                    )
                }

                composable("colaboraciones") {
                    ColaboracionesScreen(
                        volver = {
                            navController.popBackStack()
                        },
                        irContacto = {
                            navegar("contacto")
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

                composable("perfil") {

                    val usuario = estadoAuth.usuario

                    if (usuario != null) {
                        PerfilScreen(
                            nombreUsuario = usuario.nombre,
                            correoUsuario = usuario.correo,
                            volver = {
                                navController.popBackStack()
                            },
                            cerrarSesion = {
                                cerrarSesion()
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

                composable("admin") {

                    val usuario = estadoAuth.usuario

                    if (
                        usuario != null &&
                        usuario.rol == RolUsuario.ADMINISTRADOR
                    ) {
                        AdminScreen(
                            usuario = usuario,
                            volver = {
                                navController.popBackStack()
                            }
                        )
                    } else {
                        LaunchedEffect(Unit) {
                            navController.navigate("inicio") {
                                popUpTo("inicio") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                }

                composable("admin-prueba") {

                    if (esDebug) {
                        AdminPruebaScreen(
                            volver = {
                                navController.popBackStack()
                            }
                        )
                    } else {
                        LaunchedEffect(Unit) {
                            navController.navigate("inicio") {
                                popUpTo("inicio") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun coloresBarra(): NavigationBarItemColors {
    return NavigationBarItemDefaults.colors(
        selectedIconColor = RojoPrincipal,
        selectedTextColor = RojoPrincipal,
        indicatorColor = RojoClaro,
        unselectedIconColor = NegroPrincipal,
        unselectedTextColor = NegroPrincipal
    )
}

@Composable
private fun coloresDrawer(): NavigationDrawerItemColors {
    return NavigationDrawerItemDefaults.colors(
        selectedContainerColor = RojoClaro,
        selectedIconColor = RojoPrincipal,
        selectedTextColor = RojoPrincipal,
        unselectedIconColor = NegroPrincipal,
        unselectedTextColor = NegroPrincipal
    )
}
