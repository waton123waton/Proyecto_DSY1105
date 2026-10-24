package com.example.proyecto_dsy1105.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_dsy1105.model.RolUsuario
import com.example.proyecto_dsy1105.model.SolicitudContacto
import com.example.proyecto_dsy1105.model.Usuario
import com.example.proyecto_dsy1105.repository.SolicitudContactoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class AdminUiState(
    val cargando: Boolean = false,
    val solicitudes: List<SolicitudContacto> = emptyList(),
    val mensaje: String = "",
    val accesoPermitido: Boolean = false
)

class AdminViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        SolicitudContactoRepository(application)

    private val _uiState =
        MutableStateFlow(AdminUiState())

    val uiState: StateFlow<AdminUiState> =
        _uiState.asStateFlow()

    fun cargarSolicitudes(usuario: Usuario?) {

        if (usuario?.rol != RolUsuario.ADMINISTRADOR) {
            _uiState.value = AdminUiState(
                mensaje = "No tienes permisos de administrador."
            )
            return
        }

        _uiState.value = AdminUiState(
            cargando = true,
            accesoPermitido = true
        )

        viewModelScope.launch {

            val resultado = withContext(Dispatchers.IO) {
                runCatching {
                    repository.obtenerSolicitudes()
                }
            }

            _uiState.value = resultado.fold(
                onSuccess = { solicitudes ->
                    AdminUiState(
                        solicitudes = solicitudes,
                        accesoPermitido = true
                    )
                },
                onFailure = {
                    AdminUiState(
                        mensaje = "No se pudieron cargar las solicitudes.",
                        accesoPermitido = true
                    )
                }
            )
        }
    }

    fun actualizarEstado(
        usuario: Usuario?,
        id: String,
        nuevoEstado: String
    ) {

        if (usuario?.rol != RolUsuario.ADMINISTRADOR) {
            _uiState.value = AdminUiState(
                mensaje = "No tienes permisos para realizar esta acción."
            )
            return
        }

        if (_uiState.value.cargando) {
            return
        }

        _uiState.value = _uiState.value.copy(
            cargando = true,
            mensaje = ""
        )

        viewModelScope.launch {

            val resultado = withContext(Dispatchers.IO) {
                runCatching {
                    val actualizado = repository.actualizarEstado(
                        id = id,
                        nuevoEstado = nuevoEstado
                    )

                    if (actualizado) {
                        repository.obtenerSolicitudes()
                    } else {
                        null
                    }
                }
            }

            val solicitudes = resultado.getOrNull()

            if (solicitudes != null) {

                _uiState.value = AdminUiState(
                    solicitudes = solicitudes,
                    mensaje = "Estado actualizado correctamente.",
                    accesoPermitido = true
                )

            } else {

                _uiState.value = _uiState.value.copy(
                    cargando = false,
                    mensaje = "No se pudo actualizar la solicitud."
                )
            }
        }
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value.copy(
            mensaje = ""
        )
    }

    fun limpiarDatos() {
        _uiState.value = AdminUiState()
    }
}
