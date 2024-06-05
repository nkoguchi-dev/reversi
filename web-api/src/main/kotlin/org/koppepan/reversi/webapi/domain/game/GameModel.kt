package org.koppepan.reversi.webapi.domain.game

import org.koppepan.reversi.webapi.domain.board.*
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.Player
import org.koppepan.reversi.webapi.domain.player.PlayerName
import org.koppepan.reversi.webapi.domain.player.PlayerNumber
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Game private constructor(
    val gameId: GameId,
    val board: Board,
    val player1: Player,
    val player2: Player,
    val nextPlayerNumber: PlayerNumber,
) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)

        fun start(
            idGenerator: IdGenerator,
            player1Name: String,
            player2Name: String,
        ): Game {
            // 先手が黒で後手が白なのはReversiのルール
            val player1 = Player.create(PlayerName(player1Name), DiskType.Dark, PlayerNumber.PLAYER1)
            val player2 = Player.create(PlayerName(player2Name), DiskType.Light, PlayerNumber.PLAYER2)
            val game = Game(
                gameId = GameId.generate(idGenerator),
                board = Board.create(),
                player1 = player1,
                player2 = player2,
                nextPlayerNumber = player1.number,
            )
            log.debug("Gameを開始しました。 {}", game)
            return game
        }

        fun recreate(
            gameId: GameId,
            board: Board,
            player1: Player,
            player2: Player,
            nextPlayerNumber: PlayerNumber,
        ): Game {
            return Game(
                gameId = gameId,
                board = board,
                player1 = player1,
                player2 = player2,
                nextPlayerNumber = nextPlayerNumber,
            )
        }
    }

    fun getGameStatus(): GameStatus {
        return GameStatus(
            gameId,
            player1.name,
            player2.name,
            board.diskMap.getPlacedDiskMap(),
            nextPlayerNumber,
        )
    }

    fun copy(
        board: Board = this.board,
        nextPlayerNumber: PlayerNumber = this.nextPlayerNumber,
    ): Game = Game(gameId, board, player1, player2, nextPlayerNumber)

    fun putDisk(playerMove: PlayerMove): Game {
        val newBoard = board.putDisk(playerMove)
        val nextPlayerNumber = when (playerMove.number) {
            PlayerNumber.PLAYER1 -> PlayerNumber.PLAYER2
            PlayerNumber.PLAYER2 -> PlayerNumber.PLAYER1
        }
        val nextGame = this.copy(
            board = newBoard,
            nextPlayerNumber = nextPlayerNumber,
        )
        log.debug("Diskを配置しました。playerMove: {}, nextGame: {}", playerMove, nextGame)
        return nextGame
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

    override fun toString(): String {
        return "Game(gameId=$gameId, board=$board, player1=$player1, player2=$player2, nextPlayerNumber=$nextPlayerNumber)"
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

data class GameStatus(
    val gameId: GameId,
    val player1Name: PlayerName,
    val player2Name: PlayerName,
    val diskMap: DiskMap,
    val nextPlayerNumber: PlayerNumber,
)