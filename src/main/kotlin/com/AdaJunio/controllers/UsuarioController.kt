package com.AdaJunio.controllers

import com.AdaJunio.models.Usuario
import com.AdaJunio.models.UsuarioDTO
import com.AdaJunio.services.TokenService
import com.AdaJunio.services.UsuarioService
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService,
    private val tokenService: TokenService,
    private val authenticationManager: AuthenticationManager
) {

    @GetMapping("/")
    fun getAll(): List<Usuario> =
        usuarioService.findAll()

    @PostMapping("/login")
    fun login(
        @RequestBody usuarioLoginDTO: UsuarioDTO
    ): String? {

        var authentication: Authentication?
        try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    usuarioLoginDTO.nombre,
                    usuarioLoginDTO.password
                )
            )
        } catch (e: Exception) {
            println("Excepcion en authentication")
            throw ChangeSetPersister.NotFoundException()
        }

        var token: String?
        try {
            token = tokenService.generateToken(authentication)
        } catch (e: Exception) {
            println("Excepcion en generar token")
            throw Exception("Error al generar el token de autenticación")
        }

        // Retornamos el token
        return token
    }


    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<Usuario> {
        val usuario = usuarioService.findById(id)
        return ResponseEntity.ok(usuario)
    }

    @PostMapping("/")
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
