
package com.example.proyecto_dsy1105.repository

import android.content.Context
import com.example.proyecto_dsy1105.model.SolicitudContacto
import org.json.JSONArray
import org.json.JSONObject

class SolicitudContactoRepository(context: Context) {

    private val preferencias = context.applicationContext
        .getSharedPreferences(
            "solicitudes_corpaliv",
            Context.MODE_PRIVATE
        )

    private val claveSolicitudes = "solicitudes"

    // Guardar una nueva solicitud
    fun guardarSolicitud(solicitud: SolicitudContacto): Boolean {
        return try {
            val solicitudes = JSONArray(
                preferencias.getString(
                    claveSolicitudes,
                    "[]"
                ) ?: "[]"
            )

            val nuevaSolicitud = JSONObject().apply {
                put("id", solicitud.id)
                put("nombre", solicitud.nombre)
                put("correo", solicitud.correo)
                put(
                    "tipoColaboracion",
                    solicitud.tipoColaboracion
                )
                put("mensaje", solicitud.mensaje)
                put("fechaCreacion", solicitud.fechaCreacion)
                put("estado", solicitud.estado)
            }

            solicitudes.put(nuevaSolicitud)

            preferencias.edit()
                .putString(
                    claveSolicitudes,
                    solicitudes.toString()
                )
                .commit()

        } catch (e: Exception) {
            false
        }
    }

    // Recuperar las solicitudes guardadas
    fun obtenerSolicitudes(): List<SolicitudContacto> {
        return try {
            val solicitudes = JSONArray(
                preferencias.getString(
                    claveSolicitudes,
                    "[]"
                ) ?: "[]"
            )

            val lista = mutableListOf<SolicitudContacto>()

            for (i in 0 until solicitudes.length()) {
                val objeto = solicitudes.getJSONObject(i)

                val solicitud = SolicitudContacto(
                    id = objeto.getString("id"),
                    nombre = objeto.getString("nombre"),
                    correo = objeto.getString("correo"),
                    tipoColaboracion = objeto.getString(
                        "tipoColaboracion"
                    ),
                    mensaje = objeto.getString("mensaje"),
                    fechaCreacion = objeto.optLong(
                        "fechaCreacion",
                        0L
                    ),
                    estado = objeto.optString(
                        "estado",
                        "Pendiente"
                    )
                )

                lista.add(solicitud)
            }

            lista.sortedByDescending {
                it.fechaCreacion
            }

        } catch (e: Exception) {
            emptyList()
        }
    }

    // Actualizar el estado de una solicitud
    fun actualizarEstado(
        id: String,
        nuevoEstado: String
    ): Boolean {

        if (
            nuevoEstado !in listOf(
                "Pendiente",
                "En revisión",
                "Resuelta"
            )
        ) {
            return false
        }

        return try {
            val solicitudes = JSONArray(
                preferencias.getString(
                    claveSolicitudes,
                    "[]"
                ) ?: "[]"
            )

            var encontrada = false

            for (i in 0 until solicitudes.length()) {

                val objeto = solicitudes.getJSONObject(i)

                if (objeto.getString("id") == id) {
                    objeto.put("estado", nuevoEstado)
                    encontrada = true
                    break
                }
            }

            if (encontrada) {
                preferencias.edit()
                    .putString(
                        claveSolicitudes,
                        solicitudes.toString()
                    )
                    .commit()
            } else {
                false
            }

        } catch (e: Exception) {
            false
        }
    }
}
