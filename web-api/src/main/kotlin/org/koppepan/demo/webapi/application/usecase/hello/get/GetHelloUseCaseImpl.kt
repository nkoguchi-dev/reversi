package org.koppepan.demo.webapi.application.usecase.hello.get

import org.koppepan.demo.webapi.application.usecase.hello.HelloMessage
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetHelloUseCaseImpl : GetHelloUseCase {
    override fun get(input: GetHelloUseCase.Input): Mono<GetHelloUseCase.Output> {
        return Mono.just(
            GetHelloUseCase.Output(
                message = HelloMessage("Hello, ${input.name.value}!")
            )
        )
    }
}