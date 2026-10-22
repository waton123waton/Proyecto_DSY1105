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
fun ProgramasScreen(
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
                    text = "NUESTROS PROGRAMAS",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = RojoPrincipal
                )

                Text(
                    text = "Creando oportunidades",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Conoce las iniciativas de CORPALIV y " +
                            "Alba Lab que promueven la autonomía " +
                            "y la inclusión social y laboral.",
                    color = Color.White
                )
            }
        }

        Text(
            text = "Áreas de trabajo",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        ProgramaCard(
            numero = "01",
            titulo = "Formación y preparación laboral",
            descripcion = "Iniciativas orientadas al desarrollo " +
                    "de habilidades, la autonomía y la " +
                    "preparación para el empleo."
        )

        ProgramaCard(
            numero = "02",
            titulo = "Red Sociolaboral Alba Lab",
            descripcion = "Promueve oportunidades de participación, " +
                    "producción y comercialización de productos " +
                    "con impacto social."
        )

        ProgramaCard(
            numero = "03",
            titulo = "Vinculación con empresas",
            descripcion = "Fomenta iniciativas de colaboración " +
                    "con empresas, familias y donantes para " +
                    "fortalecer la inclusión."
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
                    text = "Participa en nuestras iniciativas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = NegroPrincipal
                )

                Text(
                    text = "Si deseas conocer más sobre nuestros " +
                            "programas o colaborar, puedes " +
                            "completar el formulario de contacto.",
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
private fun ProgramaCard(
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
