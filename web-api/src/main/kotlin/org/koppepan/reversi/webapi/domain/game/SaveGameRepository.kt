package org.koppepan.reversi.webapi.domain.game

@FunctionalInterface
interface SaveGameRepository {
    suspend fun save(game: Game): Game
}