package com.AdaJunio.services

import com.AdaJunio.models.Proyecto
import com.AdaJunio.models.Usuario
import com.AdaJunio.repositories.ProyectoRepository
import com.AdaJunio.repositories.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProyectoService(
    private val proyectoRepository: ProyectoRepository,
    private val usuarioRepository: UsuarioRepository
) {

    fun findAll(): List<Proyecto> =
        proyectoRepository.findAll()

    fun findById(id: Int): Proyecto =
        proyectoRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto con id=$id no encontrado")
        }

    fun create(proyecto: Proyecto): Proyecto {
        // Validar que exista el líder
        val liderId = proyecto.lider.id
        val lider: Usuario = usuarioRepository.findById(liderId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe Usuario líder con id=$liderId")
        }
        // Asociamos la instancia del líder (gestionada por JPA) al proyecto
        val proyectoToSave = proyecto.copy(lider = lider)
        return proyectoRepository.save(proyectoToSave)
    }

    fun update(id: Int, updated: Proyecto): Proyecto {
        val existing = findById(id)

        // Validar que exista el nuevo líder
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
        return proyectoRepository.save(proyectoToSave)
    }

    fun delete(id: Int) {
        if (!proyectoRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto con id=$id no encontrado")
        }
        proyectoRepository.deleteById(id)
    }
}
