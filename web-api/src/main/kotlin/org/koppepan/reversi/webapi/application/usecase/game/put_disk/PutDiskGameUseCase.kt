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
        val player1Name: String,
        val player2Name: String,
        val nextPlayer: String,
        val progress: String,
        val diskMap: Map<Position, String>
    ) {
        data class Position(
            val horizontalPosition: String,
            val verticalPosition: String,
        )
    }

    suspend fun putDisk(input: Input): Output
}