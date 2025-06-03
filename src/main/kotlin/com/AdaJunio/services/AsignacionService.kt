package com.AdaJunio.services

import com.AdaJunio.models.Asignacion
import com.AdaJunio.repositories.AsignacionRepository
import com.AdaJunio.models.Proyecto
import com.AdaJunio.repositories.ProyectoRepository
import com.AdaJunio.repositories.UsuarioRepository
import com.AdaJunio.models.AsignacionId
import com.AdaJunio.models.Usuario
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AsignacionService(
    private val asignacionRepository: AsignacionRepository,
    private val usuarioRepository: UsuarioRepository,
    private val proyectoRepository: ProyectoRepository
) {

    fun findAll(): List<Asignacion> =
        asignacionRepository.findAll()

    fun findById(id: AsignacionId): Asignacion =
        asignacionRepository.findById(id).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Asignacion con idUsuario=${id.idUsuario}, idProyecto=${id.idProyecto} no encontrada"
            )
        }

    fun create(asignacion: Asignacion): Asignacion {
        // Validar que Usuario exista
        val usuario: Usuario = usuarioRepository.findById(asignacion.idUsuario).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe Usuario con id=${asignacion.idUsuario}")
        }

        // Validar que Proyecto exista
        val proyecto: Proyecto = proyectoRepository.findById(asignacion.idProyecto).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe Proyecto con id=${asignacion.idProyecto}")
        }

        // Creamos la instancia completa (con referencias administradas por JPA)
        val asignacionToSave = Asignacion(
            idUsuario = usuario.id,
            idProyecto = proyecto.id,
            usuario = usuario,
            proyecto = proyecto
        )
        return asignacionRepository.save(asignacionToSave)
    }

    fun delete(id: AsignacionId) {
        if (!asignacionRepository.existsById(id)) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Asignacion con idUsuario=${id.idUsuario}, idProyecto=${id.idProyecto} no encontrada"
            )
        }
        asignacionRepository.deleteById(id)
    }

}
