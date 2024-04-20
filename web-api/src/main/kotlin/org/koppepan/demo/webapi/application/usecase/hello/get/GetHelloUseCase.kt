package org.koppepan.demo.webapi.application.usecase.hello.get

import org.koppepan.demo.webapi.application.usecase.hello.HelloMessage
import org.koppepan.demo.webapi.application.usecase.hello.HelloName
import reactor.core.publisher.Mono

interface GetHelloUseCase {
    fun get(input: Input): Mono<Output>

    data class Input(
        val name: HelloName
    )

    data class Output(
        val message: HelloMessage
    )
}