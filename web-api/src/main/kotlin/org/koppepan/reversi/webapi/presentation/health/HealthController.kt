package org.koppepan.reversi.webapi.presentation.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/health")
    suspend fun get(): String {
        return "ok"
    }
}