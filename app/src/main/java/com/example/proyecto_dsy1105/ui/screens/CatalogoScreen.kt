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
fun CatalogoScreen(
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
            text = "Catálogo Alba Lab",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Explora las alternativas de productos y servicios " +
                    "que pueden apoyar las iniciativas de inclusión " +
                    "social y laboral."
        )

        Text(
            text = "Categorías demostrativas",
            style = MaterialTheme.typography.titleMedium
        )

        HorizontalDivider()

        CatalogoItem(
            titulo = "Productos con impacto social",
            descripcion = "Conoce las iniciativas de producción " +
                    "y comercialización vinculadas a Alba Lab."
        )

        CatalogoItem(
            titulo = "Servicios y capacitaciones",
            descripcion = "Solicita información sobre servicios, " +
                    "formación y oportunidades de colaboración."
        )

        CatalogoItem(
            titulo = "Iniciativas empresariales",
            descripcion = "Explora oportunidades de vinculación " +
                    "y responsabilidad social empresarial."
        )

        Text(
            text = "Este catálogo es demostrativo. " +
                    "La oferta específica debe ser validada por CORPALIV.",
            style = MaterialTheme.typography.bodySmall
        )

        Button(
            onClick = irContacto,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Solicitar información")
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
private fun CatalogoItem(
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
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
