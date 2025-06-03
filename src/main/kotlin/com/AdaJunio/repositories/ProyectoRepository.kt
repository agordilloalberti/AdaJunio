package com.AdaJunio.repositories

import com.AdaJunio.models.Proyecto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProyectoRepository : JpaRepository<Proyecto, Int> {
    fun findByCompletado(completado: Boolean): List<Proyecto>
    fun findByLiderId(idLider: Int): List<Proyecto>
}
