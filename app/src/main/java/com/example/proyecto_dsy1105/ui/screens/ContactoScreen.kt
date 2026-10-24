package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_dsy1105.ui.theme.NegroPrincipal
import com.example.proyecto_dsy1105.ui.theme.RojoPrincipal
import com.example.proyecto_dsy1105.viewmodel.ContactoViewModel

@Composable
fun ContactoScreen(
    volver: () -> Unit,
    contactoViewModel: ContactoViewModel = viewModel()
) {

    var nombre by rememberSaveable {
        mutableStateOf("")
    }

    var correo by rememberSaveable {
        mutableStateOf("")
    }

    var tipoColaboracion by rememberSaveable {
        mutableStateOf("")
    }

    var mensaje by rememberSaveable {
        mutableStateOf("")
    }

    var mostrarOpciones by remember {
        mutableStateOf(false)
    }

    val estado by contactoViewModel.uiState.collectAsState()

    val opciones = listOf(
        "Donación",
        "Voluntariado",
        "Alianza empresarial",
        "Compra de productos",
        "Capacitación",
        "Otra consulta"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ENCABEZADO

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

                Text(
                    text = "CONTACTO",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = RojoPrincipal
                )

                Text(
                    text = "Hablemos de colaboración",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Completa el formulario para expresar " +
                            "tu interés en colaborar con CORPALIV " +
                            "y Alba Lab.",
                    color = Color.White
                )
            }
        }

        Text(
            text = "Formulario de contacto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        // FORMULARIO

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // NOMBRE

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        contactoViewModel.limpiarResultado()
                    },
                    enabled = !estado.cargando,
                    label = {
                        Text("Nombre completo")
                    },
                    singleLine = true,
                    isError = estado.errorNombre.isNotEmpty(),
                    trailingIcon = {
                        if (estado.errorNombre.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error en nombre",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    supportingText = {
                        AnimatedVisibility(
                            visible = estado.errorNombre.isNotEmpty()
                        ) {
                            Text(
                                text = estado.errorNombre,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // CORREO

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                        contactoViewModel.limpiarResultado()
                    },
                    enabled = !estado.cargando,
                    label = {
                        Text("Correo electrónico")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    isError = estado.errorCorreo.isNotEmpty(),
                    trailingIcon = {
                        if (estado.errorCorreo.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error en correo",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    supportingText = {
                        AnimatedVisibility(
                            visible = estado.errorCorreo.isNotEmpty()
                        ) {
                            Text(
                                text = estado.errorCorreo,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // TIPO DE COLABORACIÓN

                Text(
                    text = "Tipo de colaboración",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    OutlinedButton(
                        onClick = {
                            mostrarOpciones = true
                        },
                        enabled = !estado.cargando,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = if (tipoColaboracion.isBlank()) {
                                "Seleccionar colaboración"
                            } else {
                                tipoColaboracion
                            },
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }

                    DropdownMenu(
                        expanded = mostrarOpciones,
                        onDismissRequest = {
                            mostrarOpciones = false
                        }
                    ) {

                        opciones.forEach { opcion ->

                            DropdownMenuItem(
                                text = {
                                    Text(opcion)
                                },
                                onClick = {
                                    tipoColaboracion = opcion
                                    mostrarOpciones = false
                                    contactoViewModel.limpiarResultado()
                                }
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = estado.errorTipoColaboracion.isNotEmpty()
                ) {
                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(6.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )

                        Text(
                            text = estado.errorTipoColaboracion,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // MENSAJE

                OutlinedTextField(
                    value = mensaje,
                    onValueChange = {
                        mensaje = it
                        contactoViewModel.limpiarResultado()
                    },
                    enabled = !estado.cargando,
                    label = {
                        Text("Escribe tu mensaje")
                    },
                    minLines = 4,
                    isError = estado.errorMensaje.isNotEmpty(),
                    trailingIcon = {
                        if (estado.errorMensaje.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error en mensaje",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    supportingText = {
                        AnimatedVisibility(
                            visible = estado.errorMensaje.isNotEmpty()
                        ) {
                            Text(
                                text = estado.errorMensaje,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // ERROR GENERAL DE GUARDADO

                if (
                    estado.mensaje.isNotBlank() &&
                    !estado.guardadoExitoso &&
                    !estado.cargando
                ) {

                    Text(
                        text = estado.mensaje,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // BOTÓN ENVIAR

                Button(
                    onClick = {

                        contactoViewModel.enviarSolicitud(
                            nombre = nombre,
                            correo = correo,
                            tipoColaboracion = tipoColaboracion,
                            mensaje = mensaje
                        )
                    },
                    enabled = !estado.cargando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RojoPrincipal
                    )
                ) {

                    if (estado.cargando) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Text("Guardando solicitud...")

                    } else {

                        Text(
                            text = "Enviar solicitud",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // AVISO ACADÉMICO

        Text(
            text = "Versión académica. Utiliza solamente datos " +
                    "ficticios. Las solicitudes se guardan " +
                    "localmente en este dispositivo y no " +
                    "se envían a un servidor.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )
    }

    // CONFIRMACIÓN DE GUARDADO

    if (estado.guardadoExitoso) {

        AlertDialog(
            onDismissRequest = {
                nombre = ""
                correo = ""
                tipoColaboracion = ""
                mensaje = ""

                contactoViewModel.limpiarResultado()
            },
            title = {
                Text("Solicitud registrada")
            },
            text = {
                Text(
                    "Tu solicitud se guardó correctamente " +
                            "en el dispositivo. Podrá consultarse " +
                            "desde el panel de administración."
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        nombre = ""
                        correo = ""
                        tipoColaboracion = ""
                        mensaje = ""

                        contactoViewModel.limpiarResultado()
                    }
                ) {

                    Text("Aceptar")
                }
            }
        )
    }
}
