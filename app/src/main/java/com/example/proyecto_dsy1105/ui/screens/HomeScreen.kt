package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    irNosotros: () -> Unit,
    irProgramas: () -> Unit,
    irCatalogo: () -> Unit,
    irImpacto: () -> Unit,
    irColaboraciones: () -> Unit,
    irContacto: () -> Unit,
    irPerfil: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CORPALIV",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Alba Lab",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Inclusión social y laboral",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Conoce nuestros programas, productos e iniciativas " +
                    "para promover la autonomía y la participación " +
                    "de personas con discapacidad múltiple.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = irNosotros,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sobre nosotros")
        }

        Button(
            onClick = irProgramas,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nuestros programas")
        }

        Button(
            onClick = irCatalogo,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Catálogo de productos y servicios")
        }

        Button(
            onClick = irImpacto,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nuestro impacto social")
        }

        Button(
            onClick = irColaboraciones,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Formas de colaborar")
        }

        Button(
            onClick = irContacto,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Contáctanos")
        }

        OutlinedButton(
            onClick = irPerfil,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mi perfil")
        }
    }
}
