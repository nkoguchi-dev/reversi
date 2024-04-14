package org.koppepan.demo.webapi.presentation.hello

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@WebFluxTest(HelloController::class)
@AutoConfigureWebTestClient
class HelloControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    @DisplayName("動作確認APIが正しく動作すること")
    fun testHello() {
        webClient.get()
            .uri("/api/hello")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                {
                    "message": "Hello, World!"
                }
                """.trimIndent()
            )
    }
}