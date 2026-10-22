package com.example.proyecto_dsy1105.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = RojoPrincipal,
    onPrimary = Blanco,
    primaryContainer = RojoClaro,
    onPrimaryContainer = NegroPrincipal,
    secondary = NegroPrincipal,
    onSecondary = Blanco,
    background = FondoClaro,
    onBackground = NegroPrincipal,
    surface = Blanco,
    onSurface = NegroPrincipal,
    surfaceVariant = GrisClaro,
    onSurfaceVariant = GrisTexto,
    outline = GrisTexto,
    error = RojoOscuro
)

private val DarkColors = darkColorScheme(
    primary = RojoPrincipal,
    onPrimary = Blanco,
    secondary = RojoClaro,
    onSecondary = NegroPrincipal,
    background = NegroPrincipal,
    onBackground = Blanco,
    surface = NegroSuave,
    onSurface = Blanco
)

@Composable
fun Proyecto_DSY1105Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
