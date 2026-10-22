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
fun ColaboracionesScreen(
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
                    text = "COLABORACIONES",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = RojoPrincipal
                )

                Text(
                    text = "Sé parte del cambio",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Descubre las distintas maneras de apoyar " +
                            "las iniciativas de inclusión social y laboral " +
                            "de CORPALIV y Alba Lab.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

        Text(
            text = "¿Cómo puedes colaborar?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        ColaboracionCard(
            numero = "01",
            titulo = "Donaciones",
            descripcion = "Manifiesta tu interés en realizar aportes " +
                    "para apoyar los programas e iniciativas de CORPALIV."
        )

        ColaboracionCard(
            numero = "02",
            titulo = "Voluntariado",
            descripcion = "Consulta las oportunidades de participación " +
                    "como voluntario en las actividades de la organización."
        )

        ColaboracionCard(
            numero = "03",
            titulo = "Alianzas empresariales",
            descripcion = "Explora proyectos de colaboración y " +
                    "responsabilidad social empresarial."
        )

        ColaboracionCard(
            numero = "04",
            titulo = "Compra de productos",
            descripcion = "Conoce los productos y servicios vinculados " +
                    "a las iniciativas de Alba Lab."
        )

        ColaboracionCard(
            numero = "05",
            titulo = "Capacitaciones",
            descripcion = "Solicita información sobre iniciativas " +
                    "de formación y capacitación."
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
                    text = "¿Te interesa colaborar?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = NegroPrincipal
                )

                Text(
                    text = "Completa nuestro formulario y selecciona " +
                            "la modalidad de colaboración que te interesa.",
                    color = NegroPrincipal
                )

                Button(
                    onClick = irContacto,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Quiero colaborar")
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
private fun ColaboracionCard(
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
