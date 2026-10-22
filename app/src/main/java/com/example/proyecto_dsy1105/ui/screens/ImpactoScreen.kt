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
fun ImpactoScreen(
    volver: () -> Unit,
    irColaboraciones: () -> Unit
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
                    text = "IMPACTO SOCIAL",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = RojoPrincipal
                )

                Text(
                    text = "Cada oportunidad cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Conoce las áreas donde CORPALIV y Alba Lab " +
                            "promueven la autonomía, participación " +
                            "e inclusión social y laboral.",
                    color = Color.White
                )
            }
        }

        Text(
            text = "Áreas de impacto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        ImpactoCard(
            numero = "01",
            titulo = "Formación",
            descripcion = "Desarrollo de habilidades y preparación " +
                    "para acceder a oportunidades laborales."
        )

        ImpactoCard(
            numero = "02",
            titulo = "Inclusión laboral",
            descripcion = "Promoción de la participación y " +
                    "vinculación con el mundo del trabajo."
        )

        ImpactoCard(
            numero = "03",
            titulo = "Participación social",
            descripcion = "Iniciativas orientadas a fortalecer " +
                    "la autonomía y la integración comunitaria."
        )

        Text(
            text = "Indicadores de impacto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        IndicadorCard(
            titulo = "Personas beneficiadas",
            valor = "Por confirmar"
        )

        IndicadorCard(
            titulo = "Programas desarrollados",
            valor = "Por confirmar"
        )

        IndicadorCard(
            titulo = "Alianzas empresariales",
            valor = "Por confirmar"
        )

        Text(
            text = "Los indicadores se actualizarán cuando " +
                    "CORPALIV proporcione información autorizada.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    text = "Historias de impacto",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = NegroPrincipal
                )

                Text(
                    text = "Este espacio permitirá compartir " +
                            "experiencias y casos de éxito autorizados " +
                            "y anonimizados.",
                    color = NegroPrincipal
                )

                HorizontalDivider()

                Text(
                    text = "¿Quieres ser parte del cambio?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = NegroPrincipal
                )

                Button(
                    onClick = irColaboraciones,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Conoce cómo colaborar")
                }
            }
        }

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
private fun ImpactoCard(
    numero: String,
    titulo: String,
    descripcion: String
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
                text = numero,
                style = MaterialTheme.typography.titleMedium,
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
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun IndicadorCard(
    titulo: String,
    valor: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = valor,
                style = MaterialTheme.typography.labelLarge,
                color = RojoPrincipal,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
