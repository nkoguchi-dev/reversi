package org.koppepan.reversi.webapi.domain.game

@FunctionalInterface
interface CreateGameRepository {
    suspend fun create(game: Game): Game
}