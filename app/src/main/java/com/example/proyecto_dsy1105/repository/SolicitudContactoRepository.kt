package com.example.proyecto_dsy1105.repository

import android.content.Context
import com.example.proyecto_dsy1105.model.SolicitudContacto
import com.example.proyecto_dsy1105.network.ActualizarEstadoApi
import com.example.proyecto_dsy1105.network.NuevaSolicitudApi
import com.example.proyecto_dsy1105.network.ServicioSolicitudesApi
import com.example.proyecto_dsy1105.network.SolicitudRespuestaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.ZoneId

class SolicitudContactoRepository(context: Context) {

    private val servicio = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ServicioSolicitudesApi::class.java)

    fun guardarSolicitud(solicitud: SolicitudContacto): Boolean {
        return runCatching {
            servicio.crear(
                NuevaSolicitudApi(
                    nombre = solicitud.nombre,
                    correo = solicitud.correo,
                    tipoColaboracion = solicitud.tipoColaboracion,
                    mensaje = solicitud.mensaje
                )
            ).execute().isSuccessful
        }.getOrDefault(false)
    }

    fun obtenerSolicitudes(): List<SolicitudContacto> {
        val respuesta = servicio.listar().execute()
        if (!respuesta.isSuccessful) {
            throw IllegalStateException("Error HTTP: ${respuesta.code()}")
        }
        return respuesta.body().orEmpty().map { it.aSolicitudLocal() }
            .sortedByDescending { it.fechaCreacion }
    }

    fun actualizarEstado(id: String, nuevoEstado: String): Boolean {
        if (nuevoEstado !in listOf("Pendiente", "En revisión", "Resuelta")) {
            return false
        }
        val numeroId = id.toLongOrNull() ?: return false
        return runCatching {
            servicio.cambiarEstado(
                numeroId,
                ActualizarEstadoApi(nuevoEstado)
            ).execute().isSuccessful
        }.getOrDefault(false)
    }

    private fun SolicitudRespuestaApi.aSolicitudLocal(): SolicitudContacto {
        val fecha = runCatching {
            LocalDateTime.parse(fechaCreacion)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        }.getOrDefault(0L)

        return SolicitudContacto(
            id = id.toString(),
            nombre = nombre,
            correo = correo,
            tipoColaboracion = tipoColaboracion,
            mensaje = mensaje,
            fechaCreacion = fecha,
            estado = estado ?: "Pendiente"
        )
    }
}
