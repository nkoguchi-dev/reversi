package org.koppepan.reversi.webapi.application.usecase.game.get_state

@FunctionalInterface
interface GetGameStateUseCase {
    data class Input(
        val gameId: String,
    )

    data class Output(
        val gameId: String,
        val player1Name: String,
        val player2Name: String,
        val status: String,
    )

    suspend fun getStatus(input: Input): Output
}