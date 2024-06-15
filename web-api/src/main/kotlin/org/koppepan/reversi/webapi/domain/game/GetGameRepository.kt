package org.koppepan.reversi.webapi.domain.game

interface GetGameRepository {

    data class Input(
        val gameId: String,
    )

    suspend fun get(input: Input): Game?
}