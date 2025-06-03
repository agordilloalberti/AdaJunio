package com.AdaJunio.models

import jakarta.persistence.*

@Entity
@Table(name = "asignaciones")
@IdClass(AsignacionId::class)
data class Asignacion(
    @Id
    @Column(name = "id_usuario")
    val idUsuario: Int = 0,

    @Id
    @Column(name = "id_proyecto")
    val idProyecto: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    val usuario: Usuario? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    val proyecto: Proyecto? = null
)
