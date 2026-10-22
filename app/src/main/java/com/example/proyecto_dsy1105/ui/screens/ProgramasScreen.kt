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
fun ProgramasScreen(
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
            text = "Nuestros programas",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Conoce las iniciativas de CORPALIV y Alba Lab " +
                    "para promover la inclusión social y laboral."
        )

        HorizontalDivider()

        Text(
            text = "Formación y preparación laboral",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Iniciativas orientadas al desarrollo de habilidades, " +
                    "la autonomía y la preparación para el empleo."
        )

        HorizontalDivider()

        Text(
            text = "Red Sociolaboral Alba Lab",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Espacio de vinculación que impulsa oportunidades " +
                    "de participación, producción y comercialización " +
                    "de productos con impacto social."
        )

        HorizontalDivider()

        Text(
            text = "Vinculación con empresas",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Iniciativas de colaboración con empresas, " +
                    "familias y donantes para fortalecer la inclusión."
        )

        Spacer(modifier = Modifier.height(8.dp))

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
