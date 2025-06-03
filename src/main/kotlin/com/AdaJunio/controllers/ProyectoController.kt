package com.AdaJunio.controllers

import com.AdaJunio.models.Proyecto
import com.AdaJunio.services.ProyectoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/proyectos")
class ProyectoController(
    private val proyectoService: ProyectoService
) {

    @GetMapping
    fun getAll(): List<Proyecto> =
        proyectoService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<Proyecto> {
        val proyecto = proyectoService.findById(id)
        return ResponseEntity.ok(proyecto)
    }

    @PostMapping
    fun create(@RequestBody proyecto: Proyecto): ResponseEntity<Proyecto> {
        val creado = proyectoService.create(proyecto)
        return ResponseEntity.status(HttpStatus.CREATED).body(creado)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody proyecto: Proyecto): ResponseEntity<Proyecto> {
        val actualizado = proyectoService.update(id, proyecto)
        return ResponseEntity.ok(actualizado)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        proyectoService.delete(id)
    }
}