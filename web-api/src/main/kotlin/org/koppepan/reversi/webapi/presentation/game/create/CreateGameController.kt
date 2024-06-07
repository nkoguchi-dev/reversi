package org.koppepan.reversi.webapi.presentation.game.create

import org.koppepan.reversi.webapi.application.usecase.game.create.CreateGameUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateGameController(
    private val createGameUseCase: CreateGameUseCase,
) {
    data class CreateGameRequest(
        val player1: String,
        val player2: String,
    )

    data class CreateGameResponse(
        val gameId: String,
    )

    @PostMapping("/api/games/start")
    suspend fun get(
        @RequestBody request: CreateGameRequest,
    ): CreateGameResponse {
        val input = CreateGameUseCase.Input(
            player1Name = request.player1,
            player2Name = request.player2,
        )
        return createGameUseCase
            .create(input)
            .toResponse()
    }

    companion object {
        private fun CreateGameUseCase.Output.toResponse(): CreateGameResponse {
            return CreateGameResponse(
                gameId = this.gameId,
            )
        }
    }
}