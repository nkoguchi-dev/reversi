package org.koppepan.reversi.webapi.application.usecase.game.create

import org.komapper.r2dbc.R2dbcDatabase
import org.koppepan.reversi.webapi.domain.game.CreateGameRepository
import org.koppepan.reversi.webapi.domain.game.Game
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CreateGameUseCaseImpl(
    private val idGenerator: IdGenerator,
    private val createGameRepository: CreateGameRepository,
    private val db: R2dbcDatabase,
): CreateGameUseCase {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override suspend fun create(input: CreateGameUseCase.Input): CreateGameUseCase.Output {
        val game = Game.start(
            idGenerator = idGenerator,
            player1Name = input.player1Name,
            player2Name = input.player2Name,
        )

        db.withTransaction {
            createGameRepository.create(game)
        }

        log.debug("Gameを開始しました。 {}", game)
        return CreateGameUseCase.Output(
            gameId = game.gameId.value,
        )
    }
}