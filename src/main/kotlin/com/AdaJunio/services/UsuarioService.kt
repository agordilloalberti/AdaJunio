package com.AdaJunio.services

import com.AdaJunio.models.Usuario
import com.AdaJunio.repositories.UsuarioRepository
import com.AdaJunio.security.SecurityConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
import java.util.stream.Collectors


@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    val encoder: PasswordEncoder
): UserDetailsService {


    @Throws(UsernameNotFoundException::class)
    public override fun loadUserByUsername(username: String): UserDetails? {
        // BUSCO EL USUARIO POR SU NOMBRE EN LA BDD

        val usuario: Usuario = usuarioRepository
            .findByNombre(username)

        /* RETORNAMOS UN USERDETAILS
        El método loadUserByUsername nos fuerza a devolver un objeto de tipo UserDetails.
        Tenemos que convertir nuestro objeto de tipo Usuario a un objeto de tipo UserDetails
        ¡No os preocupéis, esto es siempre igual!
         */
        val authorities: MutableList<GrantedAuthority?> =
            Arrays.stream(usuario.roles!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority("ROLE_" + role!!.trim { it <= ' ' }) }
                .collect(Collectors.toList())

        val userDetails: UserDetails? = User // User pertenece a SpringSecurity
            .builder()
            .username(usuario.nombre)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()

        return userDetails
    }

    fun findAll(): List<Usuario> =
        usuarioRepository.findAll()

    fun findById(id: Int): Usuario =
        usuarioRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario con id=$id no encontrado")
        }

    fun create(usuario: Usuario): Usuario {
        val user = usuario.copy(
            password = encoder.encode(usuario.password)
        )
        return usuarioRepository.save(user)
    }

    fun update(id: Int, updated: Usuario): Usuario {
        val existing = findById(id)
        val usuarioToSave = existing.copy(
            nombre = updated.nombre,
            apellidos = updated.apellidos,
            telefono = updated.telefono
        )
        return usuarioRepository.save(usuarioToSave)
    }

    fun delete(id: Int) {
        if (!usuarioRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario con id=$id no encontrado")
        }
        usuarioRepository.deleteById(id)
    }
}
