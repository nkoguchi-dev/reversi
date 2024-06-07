package org.koppepan.reversi.webapi.application.usecase.game.get_state

import org.komapper.r2dbc.R2dbcDatabase
import org.koppepan.reversi.webapi.domain.game.GetGameRepository
import org.koppepan.reversi.webapi.domain.shared.CustomIllegalArgumentException
import org.springframework.stereotype.Service

@Service
class GetGameStateUseCaseImpl(
    private val getGameRepository: GetGameRepository,
    private val db: R2dbcDatabase,
): GetGameStateUseCase {
    override suspend fun getStatus(input: GetGameStateUseCase.Input): GetGameStateUseCase.Output {
        val game = db.withTransaction {
            getGameRepository.get(GetGameRepository.Input(input.gameId))
        }

        requireNotNull(game) {
            throw CustomIllegalArgumentException(
                message = "ゲームが見つかりません",
                description = "指定されたゲームIDに対応するゲームが見つかりませんでした。GameId=${input.gameId}",
            )
        }

        return GetGameStateUseCase.Output(
            gameId = game.gameId.value,
            player1Name = game.player1.name.value,
            player2Name = game.player2.name.value,
            status = game.progress.name,
        )
    }
}