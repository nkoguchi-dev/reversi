package org.koppepan.reversi.webapi.domain.game

import org.koppepan.reversi.webapi.domain.board.*
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.Player
import org.koppepan.reversi.webapi.domain.player.PlayerName

class Game private constructor(
    val gameId: GameId,
    val board: Board,
    val player1: Player,
    val player2: Player,
    val nextPlayer: Player,
) {
    companion object {
        fun start(
            idGenerator: IdGenerator,
            player1Name: String,
            player2Name: String,
        ): Game {
            // 先手が黒で後手が白なのはReversiのルール
            val player1 = Player.create(PlayerName(player1Name), DiskType.Dark)
            val player2 = Player.create(PlayerName(player2Name), DiskType.Light)
            return Game(
                gameId = GameId.generate(idGenerator),
                board = Board.create(),
                player1 = player1,
                player2 = player2,
                nextPlayer = player1,
            )
        }

        fun recreate(
            gameId: GameId,
            board: Board,
            player1Name: String,
            player2Name: String,
            nextPlayer: String,
        ): Game {
            val player1 = Player.create(PlayerName(player1Name), DiskType.Dark)
            val player2 = Player.create(PlayerName(player2Name), DiskType.Light)
            return Game(
                gameId = gameId,
                board = board,
                player1 = player1,
                player2 = player2,
                nextPlayer = when (nextPlayer) {
                    player1Name -> player1
                    player2Name -> player2
                    else -> throw IllegalArgumentException("nextPlayer is invalid")
                },
            )
        }
    }

    fun getGameStatus(): GameStatus {
        return GameStatus(
            gameId,
            player1.name,
            player2.name,
            board.diskMap.getPlacedDiskMap(),
            player1.name,
        )
    }

    fun copy(
        board: Board = this.board,
        nextPlayer: Player = this.nextPlayer,
    ): Game = Game(gameId, board, player1, player2, nextPlayer)

    fun putDisk(playerName: PlayerName, position: SquarePosition): Game {
        val disk = when (playerName) {
            player1.name -> Disk(player1.diskType)
            player2.name -> Disk(player2.diskType)
            else -> throw IllegalArgumentException("playerName is invalid")
        }
        val playerMove = PlayerMove.create(
            position = position,
            disk = disk,
        )
        val newBoard = board.putDisk(playerMove)
        val nextPlayer = when (playerName) {
            player1.name -> player2
            player2.name -> player1
            else -> throw IllegalArgumentException("playerName is invalid")
        }
        return this.copy(
            board = newBoard,
            nextPlayer = nextPlayer,
        )
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

data class GameStatus(
    val gameId: GameId,
    val player1Name: PlayerName,
    val player2Name: PlayerName,
    val diskMap: DiskMap,
    val nextPlayerName: PlayerName,
)