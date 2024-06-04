package org.koppepan.reversi.webapi.domain.game

import org.koppepan.reversi.webapi.domain.board.Board
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.Player

class Game private constructor(
    val gameId: GameId,
    val board: Board,
    val players: List<Player>,
) {
    companion object {
        fun start(idGenerator: IdGenerator, players: List<Player>): Game {
            return Game(
                GameId.generate(idGenerator),
                Board.create(),
                players,
            )
        }

        fun recreate(gameId: GameId, board: Board, players: List<Player>): Game {
            return Game(gameId, board, players)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Game) return false

        if (gameId != other.gameId) return false
        if (board != other.board) return false
        if (players != other.players) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gameId.hashCode()
        result = 31 * result + board.hashCode()
        result = 31 * result + players.hashCode()
        return result
    }
}

@JvmInline
value class GameId private constructor(
    val value: String
) {
    companion object {
        fun generate(idGenerator: IdGenerator): GameId = GameId(idGenerator.generate())

        fun recreate(value: String): GameId = GameId(value)
    }
}
