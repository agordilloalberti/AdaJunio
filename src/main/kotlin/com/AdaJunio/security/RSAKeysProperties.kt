package com.AdaJunio.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.security.PrivateKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@ConfigurationProperties(prefix = "rsa")
data class RSAKeysProperties(
    var publicKey : RSAPublicKey,
    var privateKey: RSAPrivateKey
)