package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ImpactoScreen(
    volver: () -> Unit,
    irColaboraciones: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Nuestro impacto social",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "CORPALIV y Alba Lab promueven la autonomía, " +
                    "la participación y la inclusión social y laboral " +
                    "de personas con discapacidad múltiple."
        )

        HorizontalDivider()

        Text(
            text = "Áreas de impacto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        ImpactoItem(
            titulo = "Formación",
            descripcion = "Iniciativas de desarrollo de habilidades " +
                    "y preparación para el empleo."
        )

        ImpactoItem(
            titulo = "Inclusión laboral",
            descripcion = "Promoción de oportunidades de participación " +
                    "y vinculación con el mundo del trabajo."
        )

        ImpactoItem(
            titulo = "Participación social",
            descripcion = "Acciones que buscan fortalecer la autonomía " +
                    "y la integración en la comunidad."
        )

        HorizontalDivider()

        Text(
            text = "Indicadores de impacto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Personas beneficiadas: Datos por confirmar")
                Text("Programas desarrollados: Datos por confirmar")
                Text("Alianzas empresariales: Datos por confirmar")
            }
        }

        Text(
            text = "Los indicadores oficiales se incorporarán " +
                    "cuando CORPALIV autorice su publicación.",
            style = MaterialTheme.typography.bodySmall
        )

        HorizontalDivider()

        Text(
            text = "Historias de impacto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Este espacio permitirá presentar experiencias " +
                    "y casos de éxito previamente autorizados " +
                    "y anonimizados."
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = irColaboraciones,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Conoce cómo colaborar")
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}

@Composable
private fun ImpactoItem(
    titulo: String,
    descripcion: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
