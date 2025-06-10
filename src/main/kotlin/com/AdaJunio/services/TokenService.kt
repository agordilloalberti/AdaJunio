package com.AdaJunio.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors


@Service
class TokenService {
    @Autowired
    private val jwtEncoder: JwtEncoder? = null

    fun generateToken(authentication: Authentication?): String? {
        val now = Instant.now()

        val roles: MutableList<String?> = authentication!!.authorities.stream()
            .map({ obj: GrantedAuthority? -> obj!!.authority })
            .collect(Collectors.toList())

        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(authentication.name)
            .claim("roles", roles)
            .build()

        return jwtEncoder!!.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}