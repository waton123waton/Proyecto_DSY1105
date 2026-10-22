package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyecto_dsy1105.ui.theme.NegroPrincipal
import com.example.proyecto_dsy1105.ui.theme.RojoPrincipal

@Composable
fun CatalogoScreen(
    volver: () -> Unit,
    irContacto: () -> Unit
) {
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
                    text = "CATÁLOGO ALBA LAB",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = RojoPrincipal
                )

                Text(
                    text = "Productos con propósito",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Explora las alternativas de productos, " +
                            "servicios e iniciativas que promueven " +
                            "la inclusión social y laboral.",
                    color = Color.White
                )
            }
        }

        Text(
            text = "Nuestro catálogo",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Categorías demostrativas",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        CatalogoCard(
            categoria = "PRODUCTOS",
            titulo = "Productos con impacto social",
            descripcion = "Iniciativas de producción y comercialización " +
                    "vinculadas a Alba Lab.",
            detalle = "Consulta la disponibilidad y características."
        )

        CatalogoCard(
            categoria = "SERVICIOS",
            titulo = "Servicios y capacitaciones",
            descripcion = "Alternativas de formación y servicios " +
                    "relacionados con las iniciativas de inclusión.",
            detalle = "Solicita información sobre las actividades."
        )

        CatalogoCard(
            categoria = "EMPRESAS",
            titulo = "Iniciativas empresariales",
            descripcion = "Oportunidades de vinculación y responsabilidad " +
                    "social empresarial con CORPALIV.",
            detalle = "Conoce las posibilidades de colaboración."
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "¿Te interesa algún producto o servicio?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = NegroPrincipal
                )

                Text(
                    text = "Comunícate con nosotros para conocer " +
                            "las alternativas disponibles.",
                    color = NegroPrincipal
                )

                Button(
                    onClick = irContacto,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Solicitar información")
                }
            }
        }

        Text(
            text = "Catálogo demostrativo. La oferta específica " +
                    "debe ser validada por CORPALIV.",
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
}

@Composable
private fun CatalogoCard(
    categoria: String,
    titulo: String,
    descripcion: String,
    detalle: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = categoria,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = RojoPrincipal
            )

            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider()

            Text(
                text = detalle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
