package org.koppepan.reversi.webapi.application.usecase.game.get_state

import org.koppepan.reversi.webapi.application.usecase.game.create.CreateGameUseCase

interface GetGameStateUseCase {
    data class Input(
        val gameId: String,
    )

    data class Output(
        val gameId: String,
        val player1Name: String,
        val player2Name: String,
        val nextPlayer: String,
        val progress: String,
        val diskMap: Map<CreateGameUseCase.Output.Position, String>
    )

    suspend fun getStatus(input: Input): Output
}