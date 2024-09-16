package org.koppepan.reversi.webapi.application.usecase.game.create

import org.koppepan.reversi.webapi.domain.game.Game
import org.koppepan.reversi.webapi.domain.game.SaveGameRepository
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CreateGameUseCaseImpl(
    private val idGenerator: IdGenerator,
    private val saveGameRepository: SaveGameRepository,
) : CreateGameUseCase {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override suspend fun create(input: CreateGameUseCase.Input): CreateGameUseCase.Output {
        val game = Game.start(
            idGenerator = idGenerator,
            player1Name = input.player1Name,
            player2Name = input.player2Name,
        )

        saveGameRepository.save(game)

        log.info("Gameを開始しました。 game: $game")
        return CreateGameUseCase.Output(
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