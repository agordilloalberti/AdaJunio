package com.AdaJunio.models

import jakarta.persistence.*

@Entity
@Table(name = "proyectos")
data class Proyecto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val nombre: String = "",

    val descripcion: String = "",

    val completado: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lider", nullable = false)
    val lider: Usuario
)