package org.koppepan.reversi.webapi.infrastructure.game

import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.koppepan.reversi.webapi.domain.game.CreateGameRepository
import org.koppepan.reversi.webapi.domain.game.Game
import org.koppepan.reversi.webapi.infrastructure.entity.GameEntity
import org.koppepan.reversi.webapi.infrastructure.entity.gameEntity
import org.springframework.stereotype.Repository

@Repository
class CreateGameRepositoryImpl(
    private val db: R2dbcDatabase,
): CreateGameRepository {
    override suspend fun create(game: Game): Game {
        val gameEntity = game.toEntity()
        val query = QueryDsl
            .insert(Meta.gameEntity)
            .single(gameEntity)
        db.runQuery(query)
        return game
    }

    companion object {
        private fun Game.toEntity(): GameEntity {
            return GameEntity(
                gameId = this.gameId.value,
                player1Name = this.player1.name.value,
                player2Name = this.player2.name.value,
            )
        }
    }
}