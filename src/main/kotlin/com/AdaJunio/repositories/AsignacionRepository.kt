package com.AdaJunio.repositories

import com.AdaJunio.models.Asignacion
import com.AdaJunio.models.AsignacionId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AsignacionRepository : JpaRepository<Asignacion, AsignacionId> {
    fun findByIdUsuario(idUsuario: Int): List<Asignacion>
    fun findByIdProyecto(idProyecto: Int): List<Asignacion>
}
