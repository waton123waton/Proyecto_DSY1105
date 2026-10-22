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
fun NosotrosScreen(
    volver: () -> Unit
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
                    text = "SOBRE NOSOTROS",
                    style = MaterialTheme.typography.labelLarge,
                    color = RojoPrincipal,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Conoce CORPALIV",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Trabajamos por una sociedad más inclusiva, " +
                            "promoviendo oportunidades de participación " +
                            "y desarrollo.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

        Text(
            text = "Nuestra organización",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        NosotrosCard(
            numero = "01",
            titulo = "¿Qué es CORPALIV?",
            descripcion = "Es una organización sin fines de lucro " +
                    "dedicada a promover la autonomía, participación " +
                    "e inclusión social y laboral de personas " +
                    "con discapacidad múltiple."
        )

        NosotrosCard(
            numero = "02",
            titulo = "Escuela Especial Jan Van Dijk",
            descripcion = "Forma parte de las iniciativas de CORPALIV " +
                    "orientadas a la formación y preparación " +
                    "para el empleo."
        )

        NosotrosCard(
            numero = "03",
            titulo = "Alba Lab",
            descripcion = "Es la red sociolaboral de CORPALIV, " +
                    "vinculada a la formación, producción, " +
                    "comercialización y colaboración con empresas."
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
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Nuestro propósito",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = NegroPrincipal
                )

                Text(
                    text = "Promover oportunidades de participación, " +
                            "inclusión y vinculación entre personas, " +
                            "empresas y la comunidad.",
                    color = NegroPrincipal
                )
            }
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun NosotrosCard(
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
