package org.koppepan.reversi.webapi.application.usecase.game.put_disk

interface PutDiskGameUseCase {
    data class Input(
        val gameId: String,
        val playerNumber: String,
        val horizontalPosition: String,
        val verticalPosition: String,
    )

    data class Output(
        val gameId: String,
        val player1Status: PlayerStatus,
        val player2Status: PlayerStatus,
        val nextPlayer: String,
        val progress: String,
        val diskMap: Map<Position, String>
    ) {
        data class Position(
            val horizontalPosition: String,
            val verticalPosition: String,
        )

        data class PlayerStatus(
            val name: String,
            val score: Int,
        )
    }

    suspend fun putDisk(input: Input): Output
}