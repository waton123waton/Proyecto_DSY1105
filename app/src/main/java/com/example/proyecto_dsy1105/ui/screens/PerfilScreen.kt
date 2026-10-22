
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
import com.example.proyecto_dsy1105.viewmodel.PerfilViewModel
import java.io.File

@Composable
fun PerfilScreen(
    volver: () -> Unit,
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current

    val imagenViewModel by perfilViewModel.imagenUri.collectAsState()

    // Recuperar la foto guardada anteriormente
    var imagenGuardada by remember {
        mutableStateOf(cargarImagenPerfil(context))
    }

    // Utilizar la foto guardada si existe
    val imagenUri = imagenGuardada ?: imagenViewModel

    var uriPendienteCamara by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    // Guardar una copia permanente de la imagen
    fun actualizarFoto(uri: Uri?) {
        if (uri != null) {
            try {
                val nuevaUri = guardarImagenPerfil(context, uri)
                imagenGuardada = nuevaUri

                Toast.makeText(
                    context,
                    "Foto de perfil guardada",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "No se pudo guardar la foto: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // Seleccionar imagen desde galería
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

    // Tomar fotografía
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

    // Abrir cámara
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

    // Solicitar permiso de cámara
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
            .padding(
                horizontal = 24.dp,
                vertical = 28.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Mi perfil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Personaliza tu imagen de perfil",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ImagenInteligente(
                    imagenUri = imagenUri
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = if (imagenUri == null) {
                        "Aún no has seleccionado una imagen"
                    } else {
                        "Imagen de perfil guardada"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        galeriaLauncher.launch("image/*")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Collections,
                        contentDescription = null
                    )

                    Text("  Elegir desde galería")
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        tomarFotografia()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = null
                    )

                    Text("  Tomar fotografía")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Tu foto se conservará aunque cierres " +
                    "la aplicación o cambies de pantalla.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}

// Recuperar imagen guardada
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

// Guardar imagen permanentemente
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

    val archivo = File(
        context.filesDir,
        nombreArchivo
    )

    try {
        val entrada = context.contentResolver.openInputStream(uri)
            ?: throw Exception("No se pudo abrir la imagen")

        entrada.use { input ->
            archivo.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        if (!preferencias.edit()
                .putString("foto_perfil", nombreArchivo)
                .commit()
        ) {
            throw Exception("No se pudo registrar la imagen")
        }

    } catch (e: Exception) {
        archivo.delete()
        throw e
    }

    // Eliminar la imagen anterior después de guardar la nueva
    if (anterior != null && anterior != nombreArchivo) {
        File(context.filesDir, anterior).delete()
    }

    return Uri.fromFile(archivo)
}
