package org.koppepan.demo.webapi.presentation.hello.get

import org.koppepan.demo.webapi.application.usecase.hello.HelloName
import org.koppepan.demo.webapi.application.usecase.hello.get.GetHelloUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetHelloController(
    private val getHelloUseCase: GetHelloUseCase
) {
    @GetMapping("/api/hello")
    suspend fun get(
        @RequestParam("name") name: String,
    ): HelloResponse {
        val input = GetHelloUseCase.Input(
            name = HelloName(name)
        )
        return getHelloUseCase
            .get(input)
            .toResponse()
    }

    companion object {
        private fun GetHelloUseCase.Output.toResponse(): HelloResponse {
            return HelloResponse(
                message = message.value
            )
        }
    }
}

data class HelloResponse(
    val message: String
)