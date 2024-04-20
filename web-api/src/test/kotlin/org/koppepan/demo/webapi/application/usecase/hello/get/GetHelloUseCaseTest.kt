package org.koppepan.demo.webapi.application.usecase.hello.get

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koppepan.demo.webapi.application.usecase.hello.HelloMessage
import org.koppepan.demo.webapi.application.usecase.hello.HelloName
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class GetHelloUseCaseTest {
    @InjectMocks
    private lateinit var useCase: GetHelloUseCaseImpl

    @Test
    @DisplayName("正常にHelloメッセージを返すことができる")
    fun test() {
        val input = GetHelloUseCase.Input(HelloName("John"))
        val actual = useCase.get(input)
        val expected = GetHelloUseCase.Output(
            HelloMessage("Hello, John!")
        )
        StepVerifier.create(actual)
            .expectNext(expected)
            .verifyComplete()
    }
}