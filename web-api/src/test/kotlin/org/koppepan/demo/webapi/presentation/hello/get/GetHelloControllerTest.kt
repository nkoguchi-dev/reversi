package org.koppepan.demo.webapi.presentation.hello.get

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koppepan.demo.webapi.application.usecase.hello.HelloMessage
import org.koppepan.demo.webapi.application.usecase.hello.HelloName
import org.koppepan.demo.webapi.application.usecase.hello.get.GetHelloUseCase
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@WebFluxTest(GetHelloController::class)
@AutoConfigureWebTestClient
class GetHelloControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @MockBean
    private lateinit var getHelloUseCase: GetHelloUseCase

    @Test
    @DisplayName("APIが正しく動作すること")
    fun testHello() {
        val useCaseOutput = GetHelloUseCase.Output(
            message = HelloMessage("Hello, Bob!"),
        )
        Mockito.`when`(getHelloUseCase.get(any()))
            .thenReturn(Mono.just(useCaseOutput))

        webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/api/hello")
                    .queryParam("name", "Bob")
                    .build()
            }.exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                {
                    "message": "Hello, Bob!"
                }
                """.trimIndent()
            )

        verify(getHelloUseCase, times(1))
            .get(GetHelloUseCase.Input(HelloName("Bob")))
    }
}