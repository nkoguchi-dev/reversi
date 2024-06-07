package org.koppepan.reversi.webapi.application.usecase.game.create

@FunctionalInterface
interface CreateGameUseCase {
    data class Input(
        val player1Name: String,
        val player2Name: String,
    )

    data class Output(
        val gameId: String,
    )

    suspend fun create(input: Input): Output
}