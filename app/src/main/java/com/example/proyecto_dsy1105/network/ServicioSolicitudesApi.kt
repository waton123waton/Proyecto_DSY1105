package com.example.proyecto_dsy1105.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

data class NuevaSolicitudApi(
    val nombre: String,
    val correo: String,
    val tipoColaboracion: String,
    val mensaje: String
)

data class SolicitudRespuestaApi(
    val id: Long,
    val nombre: String,
    val correo: String,
    val tipoColaboracion: String,
    val mensaje: String,
    val fechaCreacion: String?,
    val estado: String?
)

data class ActualizarEstadoApi(val estado: String)

interface ServicioSolicitudesApi {
    @GET("api/solicitudes")
    fun listar(): Call<List<SolicitudRespuestaApi>>

    @POST("api/solicitudes")
    fun crear(@Body datos: NuevaSolicitudApi): Call<SolicitudRespuestaApi>

    @PATCH("api/solicitudes/{id}/estado")
    fun cambiarEstado(
        @Path("id") id: Long,
        @Body datos: ActualizarEstadoApi
    ): Call<SolicitudRespuestaApi>
}
