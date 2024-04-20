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
            .uri { urlBuilder ->
                urlBuilder.path("/api/hello")
                    .queryParam("name", "Penguin")
                    .build()
            }.exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                {
                    "message": "Hello, Penguin!"
                }
                """.trimIndent()
            )
    }

    @Test
    @DisplayName("nameが空文字の場合400エラーが返却されること")
    fun testHelloEmptyName() {
        webTestClient
            .get()
            .uri { urlBuilder ->
                urlBuilder.path("/api/hello")
                    .queryParam("name", "")
                    .build()
            }.exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .json(
                """
                {
                    "message": "名前を設定してください",
                    "description": "HelloNameは空の値を設定する事はできません"
                }
                """.trimIndent()
            )
    }
}