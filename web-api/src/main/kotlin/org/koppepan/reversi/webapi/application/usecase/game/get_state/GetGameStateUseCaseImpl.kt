package org.koppepan.reversi.webapi.application.usecase.game.get_state

import org.komapper.r2dbc.R2dbcDatabase
import org.koppepan.reversi.webapi.application.usecase.game.create.CreateGameUseCase
import org.koppepan.reversi.webapi.application.usecase.game.exception.GameNotFoundApplicationException
import org.koppepan.reversi.webapi.domain.game.GetGameRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GetGameStateUseCaseImpl(
    private val getGameRepository: GetGameRepository,
    private val db: R2dbcDatabase,
): GetGameStateUseCase {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }


    override suspend fun getStatus(input: GetGameStateUseCase.Input): GetGameStateUseCase.Output {
        val game = db.withTransaction {
            getGameRepository.get(GetGameRepository.Input(input.gameId))
        }

        requireNotNull(game) {
            throw GameNotFoundApplicationException(
                message = "ゲームが見つかりません",
                description = "指定されたゲームが見つかりませんでした。GameId=${input.gameId}",
            )
        }

        log.info("Gameの状態を取得しました。 game: $game")
        return GetGameStateUseCase.Output(
            gameId = game.gameId.value,
            player1Name = game.player1.name.value,
            player2Name = game.player2.name.value,
            nextPlayer = game.nextPlayerNumber.name,
            progress = game.progress.value,
            diskMap = game.board.diskMap.getPlacedDiskMap()
                .value
                .entries
                .filter { (_, playerNumber) -> playerNumber != null }
                .associate { (position, playerNumber) ->
                    CreateGameUseCase.Output.Position(
                        x = position.x.value,
                        y = position.y.value,
                    ) to playerNumber!!.type.value
                },
        )
    }
}