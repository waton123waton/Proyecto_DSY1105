package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp

@Composable
fun ContactoScreen(
    volver: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var tipoColaboracion by rememberSaveable { mutableStateOf("") }
    var mensaje by rememberSaveable { mutableStateOf("") }

    var mostrarOpciones by remember { mutableStateOf(false) }
    var mostrarConfirmacion by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }

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
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Contacto y colaboración",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Completa el formulario para expresar tu interés " +
                    "en colaborar con CORPALIV y Alba Lab."
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Tipo de colaboración",
            style = MaterialTheme.typography.titleMedium
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
                onDismissRequest = { mostrarOpciones = false }
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
            label = { Text("Mensaje") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        if (mostrarError) {
            Text(
                text = "Completa todos los campos con un correo válido.",
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {
                val formularioValido =
                    nombre.isNotBlank() &&
                            android.util.Patterns.EMAIL_ADDRESS
                                .matcher(correo.trim()).matches() &&
                            tipoColaboracion.isNotBlank() &&
                            mensaje.isNotBlank()

                if (formularioValido) {
                    mostrarError = false
                    mostrarConfirmacion = true
                } else {
                    mostrarError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar solicitud")
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }

        Text(
            text = "Versión académica: utiliza solamente datos ficticios. " +
                    "El formulario aún no envía información a un servidor.",
            style = MaterialTheme.typography.bodySmall
        )
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
                    "Los datos se validaron correctamente. " +
                            "Esta versión demostrativa todavía no guarda " +
                            "ni envía solicitudes."
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
