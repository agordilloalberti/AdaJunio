package com.AdaJunio.repositories

import com.AdaJunio.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Int> {
    fun findByNombre(nombre: String): List<Usuario>
}
