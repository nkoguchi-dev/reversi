package org.koppepan.reversi.webapi.application.usecase.game.create

interface CreateGameUseCase {
    data class Input(
        val player1Name: String,
        val player2Name: String,
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
            val x: String,
            val y: String,
        )

        data class PlayerStatus(
            val name: String,
            val score: Int,
        )
    }

    suspend fun create(input: Input): Output
}