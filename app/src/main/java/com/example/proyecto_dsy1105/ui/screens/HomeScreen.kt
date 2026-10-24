package com.example.proyecto_dsy1105.ui.screens

import androidx.compose.foundation.BorderStroke
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
import com.example.proyecto_dsy1105.ui.theme.*

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
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {

        // MENSAJE DE BIENVENIDA

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = "Bienvenido a Alba Lab",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = NegroPrincipal
            )

            Text(
                text = "Conoce nuestros programas, iniciativas " +
                        "y oportunidades de colaboración.",
                style = MaterialTheme.typography.bodyMedium,
                color = GrisTexto
            )
        }

        // TARJETA PRINCIPAL

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = NegroPrincipal
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Surface(
                    color = RojoPrincipal,
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "INCLUSIÓN SOCIAL Y LABORAL",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        )
                    )
                }

                Text(
                    text = "Construyendo oportunidades",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Promovemos la autonomía, la participación " +
                            "y la inclusión de personas con " +
                            "discapacidad múltiple.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )

                Button(
                    onClick = irNosotros,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RojoPrincipal,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Text(
                        text = "Conoce CORPALIV",
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }

        // EXPLORAR

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Descubre más",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = NegroPrincipal
            )

            Text(
                text = "Explora nuestras iniciativas y conoce " +
                        "cómo puedes participar.",
                style = MaterialTheme.typography.bodySmall,
                color = GrisTexto
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                AccesoInicioCard(
                    titulo = "Quiénes somos",
                    descripcion = "Nuestra misión",
                    icono = Icons.Default.Info,
                    modifier = Modifier.weight(1f),
                    onClick = irNosotros
                )

                AccesoInicioCard(
                    titulo = "Impacto social",
                    descripcion = "Nuestro trabajo",
                    icono = Icons.Default.Favorite,
                    modifier = Modifier.weight(1f),
                    onClick = irImpacto
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                AccesoInicioCard(
                    titulo = "Contacto",
                    descripcion = "Escríbenos",
                    icono = Icons.Default.Email,
                    modifier = Modifier.weight(1f),
                    onClick = irContacto
                )

                AccesoInicioCard(
                    titulo = "Mi cuenta",
                    descripcion = "Accede a tu perfil",
                    icono = Icons.Default.Person,
                    modifier = Modifier.weight(1f),
                    onClick = irPerfil
                )
            }
        }

        // DESTACADO DE COLABORACIÓN

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = RojoClaro
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Groups,
                        contentDescription = null,
                        tint = RojoPrincipal,
                        modifier = Modifier.size(28.dp)
                    )

                    Text(
                        text = "Tu apoyo hace la diferencia",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NegroPrincipal
                    )
                }

                Text(
                    text = "Descubre cómo colaborar con los " +
                            "programas y proyectos de CORPALIV.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NegroPrincipal
                )

                Button(
                    onClick = irColaboraciones,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RojoPrincipal
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Text(
                        text = "Quiero colaborar",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // ACCESO A PROGRAMAS Y CATÁLOGO

        Text(
            text = "También puedes explorar nuestros programas " +
                    "y catálogo desde la barra inferior.",
            style = MaterialTheme.typography.bodySmall,
            color = GrisTexto
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun AccesoInicioCard(
    titulo: String,
    descripcion: String,
    icono: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .heightIn(min = 148.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = GrisClaro
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = RojoClaro
            ) {

                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = RojoPrincipal,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Text(
                text = titulo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = NegroPrincipal
            )

            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = GrisTexto
            )
        }
    }
}
