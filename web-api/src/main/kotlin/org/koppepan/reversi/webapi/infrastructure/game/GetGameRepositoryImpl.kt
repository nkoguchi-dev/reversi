package org.koppepan.reversi.webapi.infrastructure.game

import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.koppepan.reversi.webapi.domain.board.Board
import org.koppepan.reversi.webapi.domain.game.Game
import org.koppepan.reversi.webapi.domain.game.GameId
import org.koppepan.reversi.webapi.domain.game.GameProgress
import org.koppepan.reversi.webapi.domain.game.GetGameRepository
import org.koppepan.reversi.webapi.domain.player.Player
import org.koppepan.reversi.webapi.domain.player.PlayerName
import org.koppepan.reversi.webapi.domain.player.PlayerNumber
import org.koppepan.reversi.webapi.infrastructure.entity.GameEntity
import org.koppepan.reversi.webapi.infrastructure.entity.gameEntity
import org.springframework.stereotype.Repository

@Repository
class GetGameRepositoryImpl(
    private val db: R2dbcDatabase,
) : GetGameRepository {
    override suspend fun get(input: GetGameRepository.Input): Game? {
        val taskEntity = Meta.gameEntity
        val query = QueryDsl
            .from(taskEntity)
            .where {
                taskEntity.gameId eq input.gameId
            }
            .singleOrNull()
        return db.runQuery(query)
            ?.toDomain()
    }

    companion object {
        private fun GameEntity.toDomain(): Game {
            return Game.recreate(
                gameId = GameId.recreate(this.gameId),
                board = Board.create(),
                player1 = Player.createPlayer1(PlayerName(this.player1Name)),
                player2 = Player.createPlayer2(PlayerName(this.player2Name)),
                nextPlayerNumber = PlayerNumber.PLAYER1,
                progress = GameProgress.CREATED,
            )
        }
    }
}