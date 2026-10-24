package com.example.proyecto_dsy1105.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Base64
import com.example.proyecto_dsy1105.model.RolUsuario
import com.example.proyecto_dsy1105.model.Usuario
import org.json.JSONArray
import org.json.JSONObject
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

enum class ResultadoRegistro {
    EXITO,
    RUT_EXISTENTE,
    CORREO_EXISTENTE,
    ERROR_GUARDADO
}

class UsuarioRepository(
    private val context: Context
) {

    private val preferencias = context.applicationContext
        .getSharedPreferences(
            "usuarios_corpaliv",
            Context.MODE_PRIVATE
        )

    private val claveUsuarios = "usuarios"

    private fun normalizarCorreo(correo: String): String {
        return correo.trim().lowercase()
    }

    private fun normalizarRut(rut: String): String {
        return rut.uppercase()
            .replace(".", "")
            .replace("-", "")
            .replace(" ", "")
    }

    private fun obtenerUsuarios(): List<Usuario> {
        val datos = preferencias.getString(
            claveUsuarios,
            "[]"
        ) ?: "[]"

        val lista = mutableListOf<Usuario>()

        try {
            val arreglo = JSONArray(datos)

            for (i in 0 until arreglo.length()) {
                val objeto = arreglo.getJSONObject(i)

                val rolGuardado = objeto.optString(
                    "rol",
                    RolUsuario.USUARIO.name
                )

                val rol = RolUsuario.entries.find {
                    it.name == rolGuardado
                } ?: RolUsuario.USUARIO

                lista.add(
                    Usuario(
                        id = objeto.getLong("id"),
                        nombre = objeto.getString("nombre"),
                        rut = objeto.getString("rut"),
                        correo = objeto.getString("correo"),
                        passwordHash = objeto.getString(
                            "passwordHash"
                        ),
                        passwordSalt = objeto.getString(
                            "passwordSalt"
                        ),
                        rol = rol
                    )
                )
            }
        } catch (e: Exception) {
            throw IllegalStateException(
                "No se pudieron recuperar los usuarios.",
                e
            )
        }

        return lista
    }

    private fun guardarUsuarios(
        usuarios: List<Usuario>
    ): Boolean {
        val arreglo = JSONArray()

        usuarios.forEach { usuario ->
            val objeto = JSONObject().apply {
                put("id", usuario.id)
                put("nombre", usuario.nombre)
                put("rut", usuario.rut)
                put("correo", usuario.correo)
                put("passwordHash", usuario.passwordHash)
                put("passwordSalt", usuario.passwordSalt)
                put("rol", usuario.rol.name)
            }

            arreglo.put(objeto)
        }

        return preferencias.edit()
            .putString(
                claveUsuarios,
                arreglo.toString()
            )
            .commit()
    }

    fun existeRut(rut: String): Boolean {
        val rutNormalizado = normalizarRut(rut)

        return obtenerUsuarios().any {
            it.rut == rutNormalizado
        }
    }

    fun existeCorreo(correo: String): Boolean {
        val correoNormalizado = normalizarCorreo(correo)

        return obtenerUsuarios().any {
            it.correo == correoNormalizado
        }
    }

    private fun crearSalt(): String {
        val bytes = ByteArray(16)

        SecureRandom().nextBytes(bytes)

        return Base64.encodeToString(
            bytes,
            Base64.NO_WRAP
        )
    }

    private fun crearHash(
        contrasena: String,
        salt: String
    ): String {
        val saltBytes = Base64.decode(
            salt,
            Base64.NO_WRAP
        )

        val especificacion = PBEKeySpec(
            contrasena.toCharArray(),
            saltBytes,
            120000,
            256
        )

        return try {
            val resultado = SecretKeyFactory.getInstance(
                "PBKDF2WithHmacSHA256"
            ).generateSecret(
                especificacion
            ).encoded

            Base64.encodeToString(
                resultado,
                Base64.NO_WRAP
            )
        } finally {
            especificacion.clearPassword()
        }
    }

    @Synchronized
    fun registrarUsuario(
        nombre: String,
        rut: String,
        correo: String,
        contrasena: String
    ): ResultadoRegistro {
        val usuarios = obtenerUsuarios().toMutableList()

        val rutNormalizado = normalizarRut(rut)
        val correoNormalizado = normalizarCorreo(correo)

        if (
            usuarios.any {
                it.rut == rutNormalizado
            }
        ) {
            return ResultadoRegistro.RUT_EXISTENTE
        }

        if (
            usuarios.any {
                it.correo == correoNormalizado
            }
        ) {
            return ResultadoRegistro.CORREO_EXISTENTE
        }

        val salt = crearSalt()

        val nuevoUsuario = Usuario(
            id = (
                    usuarios.maxOfOrNull {
                        it.id
                    } ?: 0L
                    ) + 1L,
            nombre = nombre.trim(),
            rut = rutNormalizado,
            correo = correoNormalizado,
            passwordHash = crearHash(
                contrasena,
                salt
            ),
            passwordSalt = salt,
            rol = RolUsuario.USUARIO
        )

        usuarios.add(nuevoUsuario)

        val guardado = guardarUsuarios(usuarios)

        return if (guardado) {
            ResultadoRegistro.EXITO
        } else {
            ResultadoRegistro.ERROR_GUARDADO
        }
    }

    fun iniciarSesion(
        correo: String,
        contrasena: String
    ): Usuario? {
        val usuario = obtenerUsuarios().find {
            it.correo == normalizarCorreo(correo)
        } ?: return null

        val hashIngresado = Base64.decode(
            crearHash(
                contrasena,
                usuario.passwordSalt
            ),
            Base64.NO_WRAP
        )

        val hashGuardado = Base64.decode(
            usuario.passwordHash,
            Base64.NO_WRAP
        )

        return if (
            MessageDigest.isEqual(
                hashIngresado,
                hashGuardado
            )
        ) {
            usuario
        } else {
            null
        }
    }

    @Synchronized
    fun habilitarAdministradorDePrueba(
        correo: String,
        contrasena: String
    ): Boolean {
        val esDebug = (
                context.applicationInfo.flags and
                        ApplicationInfo.FLAG_DEBUGGABLE
                ) != 0

        if (!esDebug) {
            return false
        }

        val usuario = iniciarSesion(
            correo = correo,
            contrasena = contrasena
        ) ?: return false

        val usuarios = obtenerUsuarios().toMutableList()

        val indice = usuarios.indexOfFirst {
            it.id == usuario.id
        }

        if (indice == -1) {
            return false
        }

        if (
            usuarios[indice].rol ==
            RolUsuario.ADMINISTRADOR
        ) {
            return true
        }

        usuarios[indice] = usuarios[indice].copy(
            rol = RolUsuario.ADMINISTRADOR
        )

        return guardarUsuarios(usuarios)
    }
}
