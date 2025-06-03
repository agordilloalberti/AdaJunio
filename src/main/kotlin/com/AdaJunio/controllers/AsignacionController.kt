package com.AdaJunio.controllers

import com.AdaJunio.models.Asignacion
import com.AdaJunio.services.AsignacionService
import com.AdaJunio.models.AsignacionId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/asignaciones")
class AsignacionController(
    private val asignacionService: AsignacionService
) {

    @GetMapping
    fun getAll(): List<Asignacion> =
        asignacionService.findAll()

    @GetMapping("/{idUsuario}/{idProyecto}")
    fun getById(
        @PathVariable idUsuario: Int,
        @PathVariable idProyecto: Int
    ): ResponseEntity<Asignacion> {
        val id = AsignacionId(idUsuario, idProyecto)
        val asignacion = asignacionService.findById(id)
        return ResponseEntity.ok(asignacion)
    }

    @PostMapping
    fun create(@RequestBody asignacion: Asignacion): ResponseEntity<Asignacion> {
        val creado = asignacionService.create(asignacion)
        return ResponseEntity.status(HttpStatus.CREATED).body(creado)
    }

    @DeleteMapping("/{idUsuario}/{idProyecto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable idUsuario: Int,
        @PathVariable idProyecto: Int
    ) {
        val id = AsignacionId(idUsuario, idProyecto)
        asignacionService.delete(id)
    }
}