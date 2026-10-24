package com.example.proyecto_dsy1105.model

enum class RolUsuario {
    USUARIO,
    ADMINISTRADOR
}

data class Usuario(
    val id: Long = 0,
    val nombre: String,
    val rut: String,
    val correo: String,
    val passwordHash: String,
    val passwordSalt: String,
    val rol: RolUsuario = RolUsuario.USUARIO
)
