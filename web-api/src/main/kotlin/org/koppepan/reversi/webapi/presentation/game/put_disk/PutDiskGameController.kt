package org.koppepan.reversi.webapi.presentation.game.put_disk

import org.koppepan.reversi.webapi.application.usecase.game.put_disk.PutDiskGameUseCase
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PutDiskGameController(
    private val putDiskGameUseCase: PutDiskGameUseCase,
) {
    data class Request(
        val playerNumber: String,
        val horizontalPosition: String,
        val verticalPosition: String,
    )

    data class Response(
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

    @PutMapping("/api/games/{gameId}")
    suspend fun get(
        @PathVariable gameId: String,
        @RequestBody request: Request,
    ): Response {
        val input = PutDiskGameUseCase.Input(
            gameId = gameId,
            playerNumber = request.playerNumber,
            horizontalPosition = request.horizontalPosition,
            verticalPosition = request.verticalPosition,
        )
        return putDiskGameUseCase
            .putDisk(input)
            .toResponse()
    }

    companion object {
        private fun PutDiskGameUseCase.Output.toResponse(): Response {
            return Response(
                gameId = this.gameId,
                player1Status = Response.PlayerStatus(
                    name = this.player1Status.name,
                    score = this.player1Status.score,
                ),
                player2Status = Response.PlayerStatus(
                    name = this.player2Status.name,
                    score = this.player2Status.score,
                ),
                nextPlayer = this.nextPlayer,
                progress = this.progress,
                diskMap = this.diskMap
                    .entries
                    .associate { (position, playerType) ->
                        "${position.horizontalPosition}:${position.verticalPosition}" to playerType
                    }
            )
        }
    }
}