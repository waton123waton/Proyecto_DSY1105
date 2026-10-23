package com.example.proyecto_dsy1105.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_dsy1105.data.CamaraUriFactory
import com.example.proyecto_dsy1105.ui.components.ImagenInteligente
import com.example.proyecto_dsy1105.ui.theme.NegroPrincipal
import com.example.proyecto_dsy1105.ui.theme.RojoPrincipal
import com.example.proyecto_dsy1105.viewmodel.PerfilViewModel
import java.io.File

@Composable
fun PerfilScreen(
    volver: () -> Unit,
    nombreUsuario: String,
    correoUsuario: String,
    cerrarSesion: () -> Unit,
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current

    val imagenViewModel by perfilViewModel.imagenUri.collectAsState()

    var imagenGuardada by remember {
        mutableStateOf(cargarImagenPerfil(context))
    }

    val imagenUri = imagenGuardada ?: imagenViewModel

    var uriPendienteCamara by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    fun actualizarFoto(uri: Uri?) {
        if (uri != null) {
            try {
                imagenGuardada = guardarImagenPerfil(context, uri)

                Toast.makeText(
                    context,
                    "Foto de perfil guardada",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "No se pudo guardar la fotografía",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uriSeleccionado ->

        if (uriSeleccionado != null) {
            perfilViewModel.actualizarImagenDesdeGaleria(
                uriSeleccionado
            )

            actualizarFoto(uriSeleccionado)
        }
    }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->

        perfilViewModel.actualizarImagenDesdeCamara(
            uriPendienteCamara,
            exito
        )

        if (exito) {
            actualizarFoto(uriPendienteCamara)
        }

        uriPendienteCamara = null
    }

    fun abrirCamara() {
        try {
            val uri = CamaraUriFactory.crearUri(context)
            uriPendienteCamara = uri
            camaraLauncher.launch(uri)

        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Error al abrir la cámara: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->

        if (concedido) {
            abrirCamara()
        } else {
            Toast.makeText(
                context,
                "Debes conceder el permiso de cámara.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun tomarFotografia() {
        val permisoConcedido = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (permisoConcedido) {
            abrirCamara()
        } else {
            permisoCamaraLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                    text = "CORPALIV · ALBA LAB",
                    style = MaterialTheme.typography.labelLarge,
                    color = RojoPrincipal,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Mi perfil",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.White
                )

                Text(
                    text = "Administra tu cuenta y personaliza " +
                            "tu fotografía.",
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ImagenInteligente(
                    imagenUri = imagenUri
                )

                Text(
                    text = nombreUsuario,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = correoUsuario,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = if (imagenUri == null) {
                        "Aún no has seleccionado una imagen"
                    } else {
                        "Fotografía de perfil guardada"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )

                HorizontalDivider()

                Button(
                    onClick = {
                        galeriaLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Collections,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Elegir desde galería")
                }

                OutlinedButton(
                    onClick = {
                        tomarFotografia()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Tomar fotografía")
                }
            }
        }

        Text(
            text = "Tu fotografía se conservará al cambiar " +
                    "de pantalla o cerrar la aplicación.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }

        Button(
            onClick = cerrarSesion,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RojoPrincipal
            )
        ) {
            Text(
                text = "Cerrar sesión",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

private fun cargarImagenPerfil(context: Context): Uri? {
    val preferencias = context.getSharedPreferences(
        "perfil_preferencias",
        Context.MODE_PRIVATE
    )

    val nombreArchivo = preferencias.getString(
        "foto_perfil",
        null
    ) ?: return null

    val archivo = File(context.filesDir, nombreArchivo)

    return if (archivo.exists()) {
        Uri.fromFile(archivo)
    } else {
        null
    }
}

private fun guardarImagenPerfil(
    context: Context,
    uri: Uri
): Uri {
    val preferencias = context.getSharedPreferences(
        "perfil_preferencias",
        Context.MODE_PRIVATE
    )

    val anterior = preferencias.getString(
        "foto_perfil",
        null
    )

    val nombreArchivo = "perfil_${System.nanoTime()}.jpg"

    val archivo = File(context.filesDir, nombreArchivo)

    try {
        val entrada = context.contentResolver.openInputStream(uri)
            ?: throw Exception("No se pudo abrir la imagen")

        entrada.use { input ->
            archivo.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val guardado = preferencias.edit()
            .putString("foto_perfil", nombreArchivo)
            .commit()

        if (!guardado) {
            throw Exception("No se pudo registrar la fotografía")
        }

    } catch (e: Exception) {
        archivo.delete()
        throw e
    }

    if (anterior != null && anterior != nombreArchivo) {
        File(context.filesDir, anterior).delete()
    }

    return Uri.fromFile(archivo)
}
