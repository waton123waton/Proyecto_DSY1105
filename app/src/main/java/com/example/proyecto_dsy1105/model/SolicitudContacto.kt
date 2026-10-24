package com.example.proyecto_dsy1105.model

data class SolicitudContacto(
    val id: String,
    val nombre: String,
    val correo: String,
    val tipoColaboracion: String,
    val mensaje: String,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val estado: String = "Pendiente"
)
