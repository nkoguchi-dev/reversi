package org.koppepan.demo.webapi.presentation.hello

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HelloController {
    @GetMapping("/api/hello")
    fun hello(): Mono<HelloResponse> {
        return Mono.just(
            HelloResponse(
                message = "Hello, World!",
            )
        )
    }
}

data class HelloResponse(
    val message: String
)