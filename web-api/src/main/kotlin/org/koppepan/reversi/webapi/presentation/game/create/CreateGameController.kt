package org.koppepan.reversi.webapi.presentation.game.create

import org.koppepan.reversi.webapi.application.usecase.game.create.CreateGameUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateGameController(
    private val createGameUseCase: CreateGameUseCase,
) {
    @PostMapping("/api/game/start")
    suspend fun get(
        @RequestBody request: CreateGameRequest,
    ): CreateGameResponse {
        val input = CreateGameUseCase.Input(
            player1Name = request.player1Name,
            player2Name = request.player2Name,
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

data class CreateGameRequest(
    val player1Name: String,
    val player2Name: String,
)

data class CreateGameResponse(
    val gameId: String,
)