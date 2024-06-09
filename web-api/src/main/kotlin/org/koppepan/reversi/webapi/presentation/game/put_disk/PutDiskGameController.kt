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
    data class PutDiskGameRequest(
        val playerNumber: String,
        val horizontalPosition: String,
        val verticalPosition: String,
    )

    data class PutDiskGameResponse(
        val gameId: String,
        val player1Name: String,
        val player2Name: String,
        val nextPlayer: String,
        val progress: String,
        val diskMap: Map<String, String>
    )

    @PutMapping("/api/games/{gameId}")
    suspend fun get(
        @PathVariable gameId: String,
        @RequestBody request: PutDiskGameRequest,
    ): PutDiskGameResponse {
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
        private fun PutDiskGameUseCase.Output.toResponse(): PutDiskGameResponse {
            return PutDiskGameResponse(
                gameId = this.gameId,
                player1Name = this.player1Name,
                player2Name = this.player2Name,
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