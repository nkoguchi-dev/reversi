package org.koppepan.demo.webapi.application.usecase.hello.get

import org.koppepan.demo.webapi.application.usecase.hello.HelloMessage
import org.koppepan.demo.webapi.application.usecase.hello.HelloName

interface GetHelloUseCase {
    suspend fun get(input: Input): Output

    data class Input(
        val name: HelloName
    )

    data class Output(
        val message: HelloMessage
    )
}