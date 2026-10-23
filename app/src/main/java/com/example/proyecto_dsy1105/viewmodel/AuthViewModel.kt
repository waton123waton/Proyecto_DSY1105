package com.example.proyecto_dsy1105.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_dsy1105.model.Usuario
import com.example.proyecto_dsy1105.repository.ResultadoRegistro
import com.example.proyecto_dsy1105.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class AuthUiState(
    val cargando: Boolean = false,
    val mensaje: String = "",
    val usuario: Usuario? = null,
    val registroExitoso: Boolean = false
)

class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = UsuarioRepository(application)

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun validarRut(rut: String): Boolean {
        val limpio = rut.uppercase()
            .replace(".", "")
            .replace("-", "")
            .replace(" ", "")

        if (!limpio.matches(Regex("[0-9]{7,8}[0-9K]"))) {
            return false
        }

        val cuerpo = limpio.dropLast(1)
        val digito = limpio.last()

        var suma = 0
        var multiplicador = 2

        for (numero in cuerpo.reversed()) {
            suma += numero.digitToInt() * multiplicador

            multiplicador++
            if (multiplicador > 7) {
                multiplicador = 2
            }
        }

        val resultado = 11 - (suma % 11)

        val digitoCalculado = when (resultado) {
            11 -> '0'
            10 -> 'K'
            else -> resultado.digitToChar()
        }

        return digito == digitoCalculado
    }

    fun validarCorreo(correo: String): Boolean {
        return Patterns.EMAIL_ADDRESS
            .matcher(correo.trim())
            .matches()
    }

    fun validarContrasena(contrasena: String): Boolean {
        return contrasena.length >= 8
    }

    fun registrar(
        nombre: String,
        rut: String,
        correo: String,
        contrasena: String,
        confirmarContrasena: String
    ) {
        if (_uiState.value.cargando) return

        val error = when {
            nombre.isBlank() ->
                "Ingresa tu nombre completo."

            !validarRut(rut) ->
                "El RUT ingresado no es válido."

            !validarCorreo(correo) ->
                "Ingresa un correo electrónico válido."

            !validarContrasena(contrasena) ->
                "La contraseña debe tener al menos 8 caracteres."

            contrasena != confirmarContrasena ->
                "Las contraseñas no coinciden."

            else -> null
        }

        if (error != null) {
            _uiState.value = AuthUiState(mensaje = error)
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState(cargando = true)

            try {
                val resultado = withContext(Dispatchers.IO) {
                    repository.registrarUsuario(
                        nombre,
                        rut,
                        correo,
                        contrasena
                    )
                }

                val mensaje = when (resultado) {
                    ResultadoRegistro.EXITO ->
                        "Cuenta registrada correctamente."

                    ResultadoRegistro.RUT_EXISTENTE ->
                        "Este RUT ya está registrado."

                    ResultadoRegistro.CORREO_EXISTENTE ->
                        "Este correo ya está registrado."

                    ResultadoRegistro.ERROR_GUARDADO ->
                        "No se pudo guardar la cuenta."
                }

                _uiState.value = AuthUiState(
                    mensaje = mensaje,
                    registroExitoso = resultado == ResultadoRegistro.EXITO
                )

            } catch (e: Exception) {
                _uiState.value = AuthUiState(
                    mensaje = "Ocurrió un error al registrar la cuenta."
                )
            }
        }
    }

    fun iniciarSesion(
        correo: String,
        contrasena: String
    ) {
        if (_uiState.value.cargando) return

        if (!validarCorreo(correo) || contrasena.isBlank()) {
            _uiState.value = AuthUiState(
                mensaje = "Ingresa un correo y contraseña válidos."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState(cargando = true)

            try {
                val usuario = withContext(Dispatchers.IO) {
                    repository.iniciarSesion(correo, contrasena)
                }

                _uiState.value = if (usuario != null) {
                    AuthUiState(
                        usuario = usuario,
                        mensaje = "Sesión iniciada correctamente."
                    )
                } else {
                    AuthUiState(
                        mensaje = "Correo o contraseña incorrectos."
                    )
                }

            } catch (e: Exception) {
                _uiState.value = AuthUiState(
                    mensaje = "No se pudo iniciar sesión."
                )
            }
        }
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value.copy(
            mensaje = "",
            registroExitoso = false
        )
    }

    fun cerrarSesion() {
        _uiState.value = AuthUiState()
    }
}
