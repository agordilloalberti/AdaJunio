package com.AdaJunio.models

import jakarta.persistence.*

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val nombre: String = "",

    @Column(nullable = false)
    val apellidos: String = "",

    /*TODO:
    @Column(nullable = false
    val password: String = "",
*/


    @Column(nullable = true)
    val telefono: String? = null,

    val roles: String? = null
)