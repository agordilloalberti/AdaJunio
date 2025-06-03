package com.AdaJunio.controllers

import com.AdaJunio.services.UsuarioService
import com.AdaJunio.models.Usuario
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    @GetMapping
    fun getAll(): List<Usuario> =
        usuarioService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<Usuario> {
        val usuario = usuarioService.findById(id)
        return ResponseEntity.ok(usuario)
    }

    @PostMapping
    fun create(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val creado = usuarioService.create(usuario)
        return ResponseEntity.status(HttpStatus.CREATED).body(creado)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val actualizado = usuarioService.update(id, usuario)
        return ResponseEntity.ok(actualizado)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        usuarioService.delete(id)
    }
}
