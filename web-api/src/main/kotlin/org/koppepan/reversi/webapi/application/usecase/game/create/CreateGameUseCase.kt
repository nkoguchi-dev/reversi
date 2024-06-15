package org.koppepan.reversi.webapi.application.usecase.game.create

interface CreateGameUseCase {
    data class Input(
        val player1Name: String,
        val player2Name: String,
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
            val x: String,
            val y: String,
        )
    }

    suspend fun create(input: Input): Output
}