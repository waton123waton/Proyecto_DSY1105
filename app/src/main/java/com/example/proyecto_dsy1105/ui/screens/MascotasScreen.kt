package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MascotasScreen(
    registrarMascota: () -> Unit,
    volver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Mis mascotas")

        Text("Aquí aparecerán las mascotas registradas")

        Button(onClick = registrarMascota) {
            Text("Registrar mascota")
        }

        Button(onClick = volver) {
            Text("Volver")
        }
    }
}