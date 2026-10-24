package com.example.proyecto_dsy1105.ui.screens

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_dsy1105.repository.UsuarioRepository
import com.example.proyecto_dsy1105.ui.theme.NegroPrincipal
import com.example.proyecto_dsy1105.ui.theme.RojoPrincipal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class AdminPruebaUiState(
    val cargando: Boolean = false,
    val mensaje: String = "",
    val exitoso: Boolean = false
)

class AdminPruebaViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = UsuarioRepository(application)

    private val _uiState = MutableStateFlow(AdminPruebaUiState())

    val uiState = _uiState.asStateFlow()

    fun habilitar(correo: String, contrasena: String) {

        if (_uiState.value.cargando) return

        if (correo.isBlank() || contrasena.isBlank()) {
            _uiState.value = AdminPruebaUiState(
                mensaje = "Completa ambos campos."
            )
            return
        }

        _uiState.value = AdminPruebaUiState(
            cargando = true
        )

        viewModelScope.launch {

            val resultado = withContext(Dispatchers.IO) {
                runCatching {
                    repository.habilitarAdministradorDePrueba(
                        correo.trim(),
                        contrasena
                    )
                }.getOrDefault(false)
            }

            _uiState.value = if (resultado) {
                AdminPruebaUiState(
                    mensaje = "Cuenta habilitada como administrador. " +
                            "Cierra sesión e inicia nuevamente.",
                    exitoso = true
                )
            } else {
                AdminPruebaUiState(
                    mensaje = "No fue posible habilitar la cuenta. " +
                            "Revisa las credenciales y el modo Debug."
                )
            }
        }
    }
}

@Composable
fun AdminPruebaScreen(
    volver: () -> Unit,
    adminPruebaViewModel: AdminPruebaViewModel = viewModel()
) {

    val context = LocalContext.current

    val esDebug = (
            context.applicationInfo.flags and
                    ApplicationInfo.FLAG_DEBUGGABLE
            ) != 0

    val estado by adminPruebaViewModel.uiState.collectAsState()

    var correo by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    if (!esDebug) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Funcionalidad no disponible.")
        }

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        Icon(
            imageVector = Icons.Default.AdminPanelSettings,
            contentDescription = null,
            tint = RojoPrincipal,
            modifier = Modifier.size(52.dp)
        )

        Text(
            text = "Administrador de prueba",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = NegroPrincipal
        )

        Text(
            text = "Esta herramienta permite habilitar una " +
                    "cuenta ficticia como administrador " +
                    "durante el desarrollo.",
            style = MaterialTheme.typography.bodyMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                    },
                    label = {
                        Text("Correo de la cuenta")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true,
                    enabled = !estado.cargando && !estado.exitoso,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = {
                        contrasena = it
                    },
                    label = {
                        Text("Contraseña")
                    },
                    visualTransformation =
                        PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    singleLine = true,
                    enabled = !estado.cargando && !estado.exitoso,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        adminPruebaViewModel.habilitar(
                            correo,
                            contrasena
                        )
                    },
                    enabled = !estado.cargando && !estado.exitoso,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RojoPrincipal
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    if (estado.cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Habilitar administrador")
                    }
                }
            }
        }

        if (estado.mensaje.isNotBlank()) {

            Text(
                text = estado.mensaje,
                color = if (estado.exitoso) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
