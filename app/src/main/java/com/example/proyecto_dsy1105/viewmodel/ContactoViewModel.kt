package com.example.proyecto_dsy1105.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_dsy1105.model.SolicitudContacto
import com.example.proyecto_dsy1105.repository.SolicitudContactoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

data class ContactoUiState(
    val cargando: Boolean = false,
    val mensaje: String = "",
    val guardadoExitoso: Boolean = false,
    val errorNombre: String = "",
    val errorCorreo: String = "",
    val errorTipoColaboracion: String = "",
    val errorMensaje: String = ""
)

class ContactoViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        SolicitudContactoRepository(application)

    private val _uiState =
        MutableStateFlow(ContactoUiState())

    val uiState: StateFlow<ContactoUiState> =
        _uiState.asStateFlow()

    private fun validarNombre(nombre: String): String {
        return when {
            nombre.isBlank() ->
                "Debes ingresar tu nombre."

            nombre.trim().length < 3 ->
                "El nombre debe tener al menos 3 caracteres."

            else -> ""
        }
    }

    private fun validarCorreo(correo: String): String {
        return when {
            correo.isBlank() ->
                "Debes ingresar tu correo."

            !Patterns.EMAIL_ADDRESS.matcher(
                correo.trim()
            ).matches() ->
                "Ingresa un correo electrónico válido."

            else -> ""
        }
    }

    private fun validarTipo(
        tipoColaboracion: String
    ): String {
        return when {
            tipoColaboracion.isBlank() ->
                "Selecciona un tipo de colaboración."

            tipoColaboracion.equals(
                "Seleccionar",
                ignoreCase = true
            ) ->
                "Selecciona un tipo de colaboración."

            else -> ""
        }
    }

    private fun validarMensaje(mensaje: String): String {
        return when {
            mensaje.isBlank() ->
                "Debes escribir un mensaje."

            mensaje.trim().length < 10 ->
                "El mensaje debe tener al menos 10 caracteres."

            else -> ""
        }
    }

    fun limpiarResultado() {
        _uiState.value = ContactoUiState()
    }

    fun enviarSolicitud(
        nombre: String,
        correo: String,
        tipoColaboracion: String,
        mensaje: String
    ) {

        if (_uiState.value.cargando) {
            return
        }

        val errorNombre = validarNombre(nombre)
        val errorCorreo = validarCorreo(correo)
        val errorTipo = validarTipo(tipoColaboracion)
        val errorMensaje = validarMensaje(mensaje)

        val tieneErrores =
            errorNombre.isNotEmpty() ||
                    errorCorreo.isNotEmpty() ||
                    errorTipo.isNotEmpty() ||
                    errorMensaje.isNotEmpty()

        if (tieneErrores) {

            _uiState.value = ContactoUiState(
                mensaje = "Revisa los campos indicados.",
                errorNombre = errorNombre,
                errorCorreo = errorCorreo,
                errorTipoColaboracion = errorTipo,
                errorMensaje = errorMensaje
            )

            return
        }

        _uiState.value = ContactoUiState(
            cargando = true
        )

        viewModelScope.launch {

            val solicitud = SolicitudContacto(
                id = UUID.randomUUID().toString(),
                nombre = nombre.trim(),
                correo = correo.trim().lowercase(),
                tipoColaboracion = tipoColaboracion.trim(),
                mensaje = mensaje.trim(),
                estado = "Pendiente"
            )

            val guardado = withContext(Dispatchers.IO) {
                repository.guardarSolicitud(solicitud)
            }

            if (guardado) {

                _uiState.value = ContactoUiState(
                    mensaje = "Solicitud enviada correctamente.",
                    guardadoExitoso = true
                )

            } else {

                _uiState.value = ContactoUiState(
                    mensaje = "No se pudo guardar la solicitud."
                )
            }
        }
    }
}
