package org.koppepan.reversi.webapi.domain.game

@FunctionalInterface
interface GetGameRepository {

    data class Input(
        val gameId: String,
    )

    suspend fun get(input: Input): Game?
}