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
fun ColaboracionesScreen(
    volver: () -> Unit,
    irContacto: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Formas de colaborar",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Tu participación puede contribuir a las iniciativas " +
                    "de inclusión social y laboral de CORPALIV y Alba Lab."
        )

        HorizontalDivider()

        ColaboracionItem(
            titulo = "Donaciones",
            descripcion = "Manifiesta tu interés en realizar aportes " +
                    "para apoyar las iniciativas de la organización."
        )

        ColaboracionItem(
            titulo = "Voluntariado",
            descripcion = "Consulta las oportunidades disponibles " +
                    "para participar como voluntario."
        )

        ColaboracionItem(
            titulo = "Alianzas empresariales",
            descripcion = "Establece contacto para explorar proyectos " +
                    "de colaboración y responsabilidad social empresarial."
        )

        ColaboracionItem(
            titulo = "Compra de productos",
            descripcion = "Conoce los productos y servicios vinculados " +
                    "a las iniciativas de Alba Lab."
        )

        ColaboracionItem(
            titulo = "Capacitaciones",
            descripcion = "Solicita información sobre iniciativas " +
                    "de formación y capacitación."
        )

        HorizontalDivider()

        Text(
            text = "¿Quieres participar?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Completa el formulario de contacto para expresar " +
                    "tu interés. El equipo de vinculación podrá revisar " +
                    "tu solicitud cuando exista una integración con el servicio."
        )

        Button(
            onClick = irContacto,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quiero colaborar")
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
private fun ColaboracionItem(
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
