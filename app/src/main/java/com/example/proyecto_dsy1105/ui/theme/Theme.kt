package com.example.proyecto_dsy1105.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CorpalivColors = lightColorScheme(

    // Color principal de botones y elementos destacados
    primary = RojoPrincipal,
    onPrimary = Blanco,

    // Fondos de elementos destacados
    primaryContainer = RojoClaro,
    onPrimaryContainer = NegroPrincipal,

    // Colores secundarios
    secondary = NegroPrincipal,
    onSecondary = Blanco,

    secondaryContainer = GrisClaro,
    onSecondaryContainer = NegroPrincipal,

    // Fondo principal blanco
    background = Blanco,
    onBackground = NegroPrincipal,

    // Tarjetas y superficies
    surface = Blanco,
    onSurface = NegroPrincipal,

    surfaceVariant = GrisClaro,
    onSurfaceVariant = GrisTexto,

    // Bordes
    outline = GrisTexto,

    // Errores y validaciones
    error = RojoOscuro,
    onError = Blanco
)

@Composable
fun Proyecto_DSY1105Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CorpalivColors,
        typography = Typography,
        content = content
    )
}
