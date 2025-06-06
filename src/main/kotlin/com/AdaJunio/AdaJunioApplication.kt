package com.AdaJunio

import com.AdaJunio.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class AdaJunioApplication

fun main(args: Array<String>) {
	runApplication<AdaJunioApplication>(*args)
}
