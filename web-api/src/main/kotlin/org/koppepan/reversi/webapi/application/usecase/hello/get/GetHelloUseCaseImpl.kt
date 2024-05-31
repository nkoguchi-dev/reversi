package org.koppepan.reversi.webapi.application.usecase.hello.get

import org.koppepan.reversi.webapi.application.usecase.hello.HelloMessage
import org.springframework.stereotype.Service

@Service
class GetHelloUseCaseImpl : GetHelloUseCase {
    override suspend fun get(input: GetHelloUseCase.Input): GetHelloUseCase.Output {
        return GetHelloUseCase.Output(
            message = HelloMessage("Hello, ${input.name.value}!")
        )
    }
}