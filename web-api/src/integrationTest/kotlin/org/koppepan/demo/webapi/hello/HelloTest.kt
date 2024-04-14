package org.koppepan.demo.webapi.hello

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koppepan.demo.webapi.IntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
class HelloTest(
    @Autowired private var webTestClient: WebTestClient,
) {

    @Test
    @DisplayName("正常にHelloAPIが動作する事")
    fun testHello() {
        webTestClient
            .get()
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