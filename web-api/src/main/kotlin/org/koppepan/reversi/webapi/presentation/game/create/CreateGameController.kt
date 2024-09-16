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
        val player1Status: PlayerStatus,
        val player2Status: PlayerStatus,
        val nextPlayer: String,
        val progress: String,
        val diskMap: Map<String, String>
    ) {
        data class PlayerStatus(
            val name: String,
            val score: Int,
        )
    }

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
                player1Status = CreateGameResponse.PlayerStatus(
                    name = this.player1Status.name,
                    score = this.player1Status.score,
                ),
                player2Status = CreateGameResponse.PlayerStatus(
                    name = this.player2Status.name,
                    score = this.player2Status.score,
                ),
                nextPlayer = this.nextPlayer,
                progress = this.progress,
                diskMap = this.diskMap
                    .entries
                    .associate { (position, playerType) ->
                        "${position.x}:${position.y}" to playerType
                    }
            )
        }
    }
}