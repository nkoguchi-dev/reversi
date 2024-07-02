package org.koppepan.reversi.webapi.application.usecase.game.put_disk

import org.komapper.r2dbc.R2dbcDatabase
import org.koppepan.reversi.webapi.application.usecase.game.exception.GameNotFoundApplicationException
import org.koppepan.reversi.webapi.domain.board.HorizontalPosition
import org.koppepan.reversi.webapi.domain.board.Position
import org.koppepan.reversi.webapi.domain.board.VerticalPosition
import org.koppepan.reversi.webapi.domain.game.Game
import org.koppepan.reversi.webapi.domain.game.GetGameRepository
import org.koppepan.reversi.webapi.domain.game.SaveGameRepository
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.PlayerNumber
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PutDiskGameUseCaseImpl(
    private val idGenerator: IdGenerator,
    private val getGameRepository: GetGameRepository,
    private val saveGameRepository: SaveGameRepository,
    private val db: R2dbcDatabase,
) : PutDiskGameUseCase {
    override suspend fun putDisk(input: PutDiskGameUseCase.Input): PutDiskGameUseCase.Output {
        val requestedPlayerNumber = PlayerNumber.of(input.playerNumber)
        val position = Position(
            HorizontalPosition.of(input.horizontalPosition),
            VerticalPosition.of(input.verticalPosition),
        )

        val nextGame = db.withTransaction {
            getGameRepository
                .get(GetGameRepository.Input(input.gameId))
                ?.putDisk(idGenerator, requestedPlayerNumber, position)
                ?.save()
                ?: throw GameNotFoundApplicationException(
                    message = "ディスクを配置する事ができません",
                    description = "指定されたゲームが見つかりませんでした。GameId=${input.gameId}",
                )
        }

        log.info("ディスクを配置しました。 game: $nextGame")
        return PutDiskGameUseCase.Output(
            gameId = nextGame.gameId.value,
            player1Name = nextGame.player1.name.value,
            player2Name = nextGame.player2.name.value,
            nextPlayer = nextGame.nextPlayerNumber.value,
            progress = nextGame.progress.value,
            diskMap = nextGame.board.diskMap.getPlacedDiskMap()
                .value
                .entries
                .filter { (_, playerNumber) -> playerNumber != null }
                .associate { (position, playerNumber) ->
                    PutDiskGameUseCase.Output.Position(
                        horizontalPosition = position.x.value,
                        verticalPosition = position.y.value,
                    ) to playerNumber!!.type.value
                },
        )
    }

    private suspend fun Game.save(): Game {
        saveGameRepository.save(this)
        return this
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)

        private fun Game.putDisk(idGenerator: IdGenerator, playerNumber: PlayerNumber, position: Position): Game {
            val move = when (playerNumber) {
                PlayerNumber.PLAYER1 -> player1
                PlayerNumber.PLAYER2 -> player2
            }.createMove(idGenerator, position)
            return this.putDisk(move)
        }
    }
}