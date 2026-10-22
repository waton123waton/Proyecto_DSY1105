package com.example.proyecto_dsy1105.ui.screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyecto_dsy1105.ui.theme.NegroPrincipal
import com.example.proyecto_dsy1105.ui.theme.RojoPrincipal

@Composable
fun ContactoScreen(
    volver: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var tipoColaboracion by rememberSaveable { mutableStateOf("") }
    var mensaje by rememberSaveable { mutableStateOf("") }

    var mostrarOpciones by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }
    var mostrarConfirmacion by remember { mutableStateOf(false) }

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
                            "tu interés en colaborar con CORPALIV y Alba Lab.",
                    color = Color.White
                )
            }
        }

        Text(
            text = "Formulario de contacto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

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
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Tipo de colaboración",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )

                Box {
                    OutlinedButton(
                        onClick = { mostrarOpciones = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (tipoColaboracion.isEmpty())
                                "Seleccionar colaboración"
                            else
                                tipoColaboracion
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
                                text = { Text(opcion) },
                                onClick = {
                                    tipoColaboracion = opcion
                                    mostrarOpciones = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = mensaje,
                    onValueChange = { mensaje = it },
                    label = { Text("Escribe tu mensaje") },
                    minLines = 4,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (mostrarError) {
                    Text(
                        text = "Completa todos los campos e ingresa " +
                                "un correo electrónico válido.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = {
                        val valido =
                            nombre.isNotBlank() &&
                                    Patterns.EMAIL_ADDRESS
                                        .matcher(correo.trim()).matches() &&
                                    tipoColaboracion.isNotBlank() &&
                                    mensaje.isNotBlank()

                        if (valido) {
                            mostrarError = false
                            mostrarConfirmacion = true
                        } else {
                            mostrarError = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RojoPrincipal
                    )
                ) {
                    Text(
                        text = "Enviar solicitud",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Text(
            text = "Versión académica. Utiliza solamente datos " +
                    "ficticios. El formulario todavía no envía " +
                    "información a un servidor.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(12.dp))
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacion = false
            },
            title = {
                Text("Formulario validado")
            },
            text = {
                Text(
                    "Los datos ingresados son válidos. " +
                            "Esta versión demostrativa todavía no " +
                            "guarda ni envía solicitudes."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacion = false
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
