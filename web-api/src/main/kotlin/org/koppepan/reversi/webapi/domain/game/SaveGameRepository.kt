package org.koppepan.reversi.webapi.domain.game

interface SaveGameRepository {
    suspend fun save(game: Game): Game
}