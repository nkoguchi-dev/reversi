package org.koppepan.reversi.webapi.domain.game

import org.koppepan.reversi.webapi.domain.board.Board
import org.koppepan.reversi.webapi.domain.board.DiskType
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.Player
import org.koppepan.reversi.webapi.domain.player.PlayerName

class Game private constructor(
    val gameId: GameId,
    val board: Board,
    val player1: Player,
    val player2: Player,
) {
    companion object {
        fun start(
            idGenerator: IdGenerator,
            player1Name: String,
            player2Name: String,
        ): Game {
            // 先手が黒で後手が白なのはReversiのルール
            return Game(
                GameId.generate(idGenerator),
                Board.create(),
                Player.create(PlayerName(player1Name), DiskType.Dark),
                Player.create(PlayerName(player2Name), DiskType.Light),
            )
        }

        fun recreate(
            gameId: GameId,
            board: Board,
            player1Name: String,
            player2Name: String,
        ): Game {
            return Game(
                gameId,
                board,
                Player.create(PlayerName(player1Name), DiskType.Dark),
                Player.create(PlayerName(player2Name), DiskType.Light),
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Game) return false

        if (gameId != other.gameId) return false
        if (board != other.board) return false
        if (player1 != other.player1) return false
        if (player2 != other.player2) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gameId.hashCode()
        result = 31 * result + board.hashCode()
        result = 31 * result + player1.hashCode()
        result = 31 * result + player2.hashCode()
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
