package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyecto_dsy1105.ui.theme.NegroPrincipal
import com.example.proyecto_dsy1105.ui.theme.RojoPrincipal

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
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "CORPALIV",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "ALBA LAB",
            style = MaterialTheme.typography.labelLarge,
            color = RojoPrincipal
        )

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = NegroPrincipal
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Construyendo oportunidades",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Promovemos la inclusión social y laboral " +
                            "de personas con discapacidad múltiple.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )

                Button(
                    onClick = irNosotros,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RojoPrincipal
                    )
                ) {
                    Text("Conócenos")
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }

        Text(
            text = "Explora CORPALIV",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MenuCard(
                titulo = "Nosotros",
                icono = Icons.Default.Info,
                modifier = Modifier.weight(1f),
                onClick = irNosotros
            )

            MenuCard(
                titulo = "Programas",
                icono = Icons.Default.School,
                modifier = Modifier.weight(1f),
                onClick = irProgramas
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MenuCard(
                titulo = "Catálogo",
                icono = Icons.Default.ShoppingBag,
                modifier = Modifier.weight(1f),
                onClick = irCatalogo
            )

            MenuCard(
                titulo = "Impacto social",
                icono = Icons.Default.Favorite,
                modifier = Modifier.weight(1f),
                onClick = irImpacto
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MenuCard(
                titulo = "Colaboraciones",
                icono = Icons.Default.Groups,
                modifier = Modifier.weight(1f),
                onClick = irColaboraciones
            )

            MenuCard(
                titulo = "Contacto",
                icono = Icons.Default.Email,
                modifier = Modifier.weight(1f),
                onClick = irContacto
            )
        }

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MenuCard(
                    titulo = "Perfil y cámara",
                    icono = Icons.Default.CameraAlt,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = irPerfil
                )

                Text(
                    text = "Tu colaboración importa",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Conoce las distintas maneras de apoyar " +
                            "las iniciativas de CORPALIV y Alba Lab."
                )

                Button(
                    onClick = irColaboraciones,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Quiero colaborar")
                }
            }
        }

        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun MenuCard(
    titulo: String,
    icono: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = RojoPrincipal,
                modifier = Modifier.size(30.dp)
            )

            Text(
                text = titulo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
