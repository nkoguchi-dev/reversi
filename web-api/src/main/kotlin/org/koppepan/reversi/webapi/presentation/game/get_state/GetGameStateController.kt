package org.koppepan.reversi.webapi.presentation.game.get_state

import org.koppepan.reversi.webapi.application.usecase.game.get_state.GetGameStateUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetGameStateController(
    private val getGameStateUseCase: GetGameStateUseCase,
) {
    data class GetGameStateResponse(
        val gameId: String,
        val player1Name: String,
        val player2Name: String,
        val status: String,
    )

    @GetMapping("/api/games/{gameId}")
    suspend fun get(
        @PathVariable gameId: String,
    ): GetGameStateResponse {
        val input = GetGameStateUseCase.Input(
            gameId = gameId,
        )
        return getGameStateUseCase
            .getStatus(input)
            .toResponse()
    }

    companion object {
        private fun GetGameStateUseCase.Output.toResponse(): GetGameStateResponse {
            return GetGameStateResponse(
                gameId = this.gameId,
                player1Name = this.player1Name,
                player2Name = this.player2Name,
                status = this.status,
            )
        }
    }
}