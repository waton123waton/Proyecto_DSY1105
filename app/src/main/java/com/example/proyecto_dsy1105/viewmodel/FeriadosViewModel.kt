package com.example.proyecto_dsy1105.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_dsy1105.network.ClienteFeriados
import com.example.proyecto_dsy1105.network.FeriadoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

data class EstadoFeriados(
    val cargando: Boolean = false,
    val feriados: List<FeriadoApi> = emptyList(),
    val error: String? = null
)

class FeriadosViewModel : ViewModel() {
    var estado by mutableStateOf(EstadoFeriados())
        private set

    init {
        cargar()
    }

    fun cargar() {
        if (estado.cargando) return

        estado = EstadoFeriados(cargando = true)
        viewModelScope.launch {
            try {
                val proximos = withContext(Dispatchers.IO) {
                    val hoy = LocalDate.now()
                    var resultados = consultar(hoy.year)
                        .filter { LocalDate.parse(it.date) >= hoy }

                    if (resultados.isEmpty()) {
                        resultados = consultar(hoy.year + 1)
                    }
                    resultados.sortedBy { it.date }.take(5)
                }
                estado = EstadoFeriados(feriados = proximos)
            } catch (error: Exception) {
                estado = EstadoFeriados(
                    error = "No se pudieron consultar los feriados. Comprueba Internet."
                )
            }
        }
    }

    private fun consultar(year: Int): List<FeriadoApi> {
        val respuesta = ClienteFeriados.servicio.obtenerFeriados(year).execute()
        if (!respuesta.isSuccessful) {
            throw IllegalStateException("Error HTTP: ${respuesta.code()}")
        }
        return respuesta.body().orEmpty()
    }
}
