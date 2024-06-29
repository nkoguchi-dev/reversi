package org.koppepan.reversi.webapi.health

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koppepan.reversi.webapi.IntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@IntegrationTest
class HealthTest(
    @Autowired private var webTestClient: WebTestClient,
) {
    @Test
    @DisplayName("正常にHealthチェックに応答できること")
    fun test() = runTest {
        val response = webTestClient
            .get()
            .uri("/health")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .returnResult()

        assertEquals("ok", response.responseBody)
    }
}