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
fun NosotrosScreen(
    volver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Sobre nosotros",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "¿Qué es CORPALIV?",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "CORPALIV es una organización sin fines de lucro " +
                    "dedicada a promover la autonomía, participación e " +
                    "inclusión social y laboral de personas con " +
                    "discapacidad múltiple."
        )

        Text(
            text = "Escuela Especial Jan Van Dijk",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Forma parte de las iniciativas de CORPALIV " +
                    "orientadas a la formación y preparación para el empleo."
        )

        Text(
            text = "Alba Lab",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Es la red sociolaboral de CORPALIV, vinculada " +
                    "a la formación, producción, comercialización " +
                    "de productos y colaboración con empresas."
        )

        Text(
            text = "Nuestro propósito",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Promover oportunidades de participación, " +
                    "inclusión y vinculación entre personas, " +
                    "empresas y la comunidad."
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
