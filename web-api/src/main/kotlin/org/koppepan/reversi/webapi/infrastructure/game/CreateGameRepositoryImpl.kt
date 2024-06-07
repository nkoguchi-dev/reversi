package org.koppepan.reversi.webapi.infrastructure.game

import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.koppepan.reversi.webapi.domain.game.CreateGameRepository
import org.koppepan.reversi.webapi.domain.game.Game
import org.koppepan.reversi.webapi.infrastructure.entity.DiskMapEntity
import org.koppepan.reversi.webapi.infrastructure.entity.GameEntity
import org.koppepan.reversi.webapi.infrastructure.entity.diskMapEntity
import org.koppepan.reversi.webapi.infrastructure.entity.gameEntity
import org.springframework.stereotype.Repository

@Repository
class CreateGameRepositoryImpl(
    private val db: R2dbcDatabase,
) : CreateGameRepository {
    override suspend fun create(game: Game): Game {
        val insertGameQuery = QueryDsl
            .insert(Meta.gameEntity)
            .single(game.toGameEntity())
        db.runQuery(insertGameQuery)

        val upsertDiskMapQuery = QueryDsl
            .insert(Meta.diskMapEntity)
            .onDuplicateKeyUpdate()
            .multiple(game.toDiskMapEntity())
        db.runQuery(upsertDiskMapQuery)
        return game
    }

    companion object {
        private fun Game.toGameEntity(): GameEntity {
            return GameEntity(
                gameId = this.gameId.value,
                player1Name = this.player1.name.value,
                player2Name = this.player2.name.value,
                status = this.progress.value,
            )
        }

        private fun Game.toDiskMapEntity(): List<DiskMapEntity> {
            return this.board.diskMap.value
                .filter { (_, disk) -> disk != null }
                .map { (position, disk) ->
                    DiskMapEntity(
                        gameId = this.gameId.value,
                        horizontalPosition = position.x.value,
                        verticalPosition = position.y.value,
                        diskType = disk!!.type.value,
                    )
                }
                .toList()
        }
    }
}