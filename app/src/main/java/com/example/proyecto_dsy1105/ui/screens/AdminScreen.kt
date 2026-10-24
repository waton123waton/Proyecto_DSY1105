package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_dsy1105.model.RolUsuario
import com.example.proyecto_dsy1105.model.SolicitudContacto
import com.example.proyecto_dsy1105.model.Usuario
import com.example.proyecto_dsy1105.ui.theme.NegroPrincipal
import com.example.proyecto_dsy1105.ui.theme.RojoClaro
import com.example.proyecto_dsy1105.ui.theme.RojoPrincipal
import com.example.proyecto_dsy1105.viewmodel.AdminViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminScreen(
    usuario: Usuario,
    volver: () -> Unit,
    adminViewModel: AdminViewModel = viewModel()
) {

    val estado by adminViewModel.uiState.collectAsState()

    val esAdministrador =
        usuario.rol == RolUsuario.ADMINISTRADOR

    LaunchedEffect(usuario.id, usuario.rol) {
        if (esAdministrador) {
            adminViewModel.cargarSolicitudes(usuario)
        } else {
            adminViewModel.limpiarDatos()
        }
    }

    if (!esAdministrador) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Acceso restringido",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Esta sección está disponible " +
                        "únicamente para administradores."
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = volver,
                colors = ButtonDefaults.buttonColors(
                    containerColor = RojoPrincipal
                )
            ) {
                Text("Volver")
            }
        }

        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = NegroPrincipal
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Assignment,
                        contentDescription = null,
                        tint = RojoPrincipal,
                        modifier = Modifier.size(34.dp)
                    )

                    Text(
                        text = "Panel de administración",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Bienvenido, ${usuario.nombre}",
                        color = Color.White
                    )

                    Text(
                        text = "Consulta y administra las " +
                                "solicitudes recibidas desde Contacto.",
                        color = Color.White
                    )
                }
            }
        }

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text = "Solicitudes recibidas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = NegroPrincipal
                    )

                    Text(
                        text = "${estado.solicitudes.size} registros",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme
                            .onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = {
                        adminViewModel.cargarSolicitudes(usuario)
                    },
                    enabled = !estado.cargando
                ) {

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Actualizar solicitudes",
                        tint = RojoPrincipal
                    )
                }
            }
        }

        if (estado.cargando) {

            item {

                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = RojoPrincipal
                )
            }
        }

        if (estado.mensaje.isNotBlank()) {

            item {

                Text(
                    text = estado.mensaje,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (
                        estado.mensaje.contains(
                            "correctamente",
                            ignoreCase = true
                        )
                    ) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
            }
        }

        if (
            !estado.cargando &&
            estado.solicitudes.isEmpty()
        ) {

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = RojoClaro
                    )
                ) {

                    Text(
                        text = "Todavía no hay solicitudes registradas.",
                        modifier = Modifier.padding(20.dp),
                        color = NegroPrincipal
                    )
                }
            }
        }

        items(
            items = estado.solicitudes,
            key = { it.id }
        ) { solicitud ->

            SolicitudAdminCard(
                solicitud = solicitud,
                habilitado = !estado.cargando,
                cambiarEstado = { nuevoEstado ->

                    adminViewModel.actualizarEstado(
                        usuario = usuario,
                        id = solicitud.id,
                        nuevoEstado = nuevoEstado
                    )
                }
            )
        }

        item {

            OutlinedButton(
                onClick = volver,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Volver")
            }
        }
    }
}

@Composable
private fun SolicitudAdminCard(
    solicitud: SolicitudContacto,
    habilitado: Boolean,
    cambiarEstado: (String) -> Unit
) {

    var menuExpandido by remember(solicitud.id) {
        mutableStateOf(false)
    }

    val formatoFecha = remember {
        SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale("es", "CL")
        )
    }

    val fecha = remember(solicitud.fechaCreacion) {
        formatoFecha.format(
            Date(solicitud.fechaCreacion)
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = solicitud.tipoColaboracion,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = NegroPrincipal
            )

            Surface(
                shape = RoundedCornerShape(50.dp),
                color = when (solicitud.estado) {
                    "Resuelta" -> Color(0xFFE5F5EA)
                    "En revisión" -> Color(0xFFFFF1D6)
                    else -> RojoClaro
                }
            ) {

                Text(
                    text = solicitud.estado,
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 6.dp
                    ),
                    color = NegroPrincipal,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider()

            Text(
                text = "Nombre: ${solicitud.nombre}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Correo: ${solicitud.correo}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Fecha: $fecha",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Mensaje",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = solicitud.mensaje,
                style = MaterialTheme.typography.bodyMedium
            )

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedButton(
                    onClick = {
                        menuExpandido = true
                    },
                    enabled = habilitado,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Cambiar estado",
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }

                DropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = {
                        menuExpandido = false
                    }
                ) {

                    listOf(
                        "Pendiente",
                        "En revisión",
                        "Resuelta"
                    ).forEach { opcion ->

                        DropdownMenuItem(
                            text = {
                                Text(opcion)
                            },
                            onClick = {
                                menuExpandido = false

                                if (
                                    opcion != solicitud.estado
                                ) {
                                    cambiarEstado(opcion)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
