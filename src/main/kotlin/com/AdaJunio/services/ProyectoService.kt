package com.AdaJunio.services

import com.AdaJunio.models.Asignacion
import com.AdaJunio.models.AsignacionId
import com.AdaJunio.models.Proyecto
import com.AdaJunio.models.Usuario
import com.AdaJunio.repositories.AsignacionRepository
import com.AdaJunio.repositories.ProyectoRepository
import com.AdaJunio.repositories.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProyectoService(
    private val proyectoRepository: ProyectoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val asignacionService: AsignacionService,
    private val asignacionRepository: AsignacionRepository
) {

    fun findAll(): List<Proyecto> =
        proyectoRepository.findAll()

    fun findById(id: Int): Proyecto =
        proyectoRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto con id=$id no encontrado")
        }

    fun create(proyecto: Proyecto): Proyecto {
        val liderId = proyecto.lider.id
        val lider: Usuario = usuarioRepository.findById(liderId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe Usuario líder con id=$liderId")
        }

        val proyectoToSave = proyecto.copy(lider = lider)
        val savedProyecto = proyectoRepository.save(proyectoToSave)

        asignacionService.create(
            Asignacion(
                idUsuario = lider.id,
                idProyecto = savedProyecto.id,
                usuario = lider,
                proyecto = savedProyecto
            )
        )

        return savedProyecto
    }

    fun update(id: Int, updated: Proyecto): Proyecto {
        val existing = findById(id)

        val newLiderId = updated.lider.id
        val newLider: Usuario = usuarioRepository.findById(newLiderId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe Usuario líder con id=$newLiderId")
        }

        val proyectoToSave = existing.copy(
            nombre = updated.nombre,
            descripcion = updated.descripcion,
            completado = updated.completado,
            lider = newLider
        )
        val saved = proyectoRepository.save(proyectoToSave)

        if (existing.lider.id != newLider.id) {
            val newAsignacionId = AsignacionId(newLider.id, saved.id)

            if (!asignacionRepository.existsById(newAsignacionId)) {
                asignacionService.create(
                    Asignacion(
                        idUsuario = newLider.id,
                        idProyecto = saved.id,
                        usuario = newLider,
                        proyecto = saved
                    )
                )
            }

            val oldAsignacionId = AsignacionId(existing.lider.id, saved.id)
            if (asignacionRepository.existsById(oldAsignacionId)) {
                asignacionService.delete(oldAsignacionId)
            }
        }

        return saved
    }

    fun delete(id: Int) {
        if (!proyectoRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto con id=$id no encontrado")
        }

        val asignaciones = asignacionRepository.findAll()
            .filter { it.idProyecto == id }

        asignacionRepository.deleteAll(asignaciones)

        proyectoRepository.deleteById(id)
    }
}
