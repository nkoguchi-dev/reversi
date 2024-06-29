package org.koppepan.reversi.webapi.presentation.health

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @GetMapping("/health")
    suspend fun get(): String {
        log.info("Health check")
        return "ok"
    }
}