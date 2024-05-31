package org.koppepan.reversi.webapi.presentation.hello.get

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koppepan.reversi.webapi.application.usecase.hello.HelloMessage
import org.koppepan.reversi.webapi.application.usecase.hello.HelloName
import org.koppepan.reversi.webapi.application.usecase.hello.get.GetHelloUseCase
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

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
    fun testHello(): Unit = runBlocking {
        val useCaseOutput = GetHelloUseCase.Output(
            message = HelloMessage("Hello, Bob!"),
        )
        whenever(getHelloUseCase.get(any()))
            .thenReturn(useCaseOutput)

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