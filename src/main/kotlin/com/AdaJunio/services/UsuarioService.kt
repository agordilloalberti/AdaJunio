package com.AdaJunio.services

import com.AdaJunio.models.Usuario
import com.AdaJunio.repositories.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository
) {

    fun findAll(): List<Usuario> =
        usuarioRepository.findAll()

    fun findById(id: Int): Usuario =
        usuarioRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario con id=$id no encontrado")
        }

    fun create(usuario: Usuario): Usuario =
        usuarioRepository.save(usuario)

    fun update(id: Int, updated: Usuario): Usuario {
        val existing = findById(id)
        // Actualizamos solo campos permitidos; el ID no cambia
        val usuarioToSave = existing.copy(
            nombre = updated.nombre,
            apellidos = updated.apellidos,
            telefono = updated.telefono
        )
        return usuarioRepository.save(usuarioToSave)
    }

    fun delete(id: Int) {
        if (!usuarioRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario con id=$id no encontrado")
        }
        usuarioRepository.deleteById(id)
    }
}
