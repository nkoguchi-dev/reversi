package org.koppepan.reversi.webapi.domain.game

import org.koppepan.reversi.webapi.domain.board.Board
import org.koppepan.reversi.webapi.domain.board.DiskMap
import org.koppepan.reversi.webapi.domain.game.exception.GameAlreadyFinishedDomainException
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.Move
import org.koppepan.reversi.webapi.domain.player.Player
import org.koppepan.reversi.webapi.domain.player.PlayerName
import org.koppepan.reversi.webapi.domain.player.PlayerNumber
import org.koppepan.reversi.webapi.domain.shared.IllegalArgumentDomainException

class Game private constructor(
    val gameId: GameId,
    val board: Board,
    val player1: Player,
    val player2: Player,
    val nextPlayerNumber: PlayerNumber,
    val progress: GameProgress,
) {
    companion object {
        fun start(
            idGenerator: IdGenerator,
            player1Name: String,
            player2Name: String,
        ): Game {
            val player1 = Player.createPlayer1(PlayerName(player1Name))
            val player2 = Player.createPlayer2(PlayerName(player2Name))
            val game = Game(
                gameId = GameId.generate(idGenerator),
                board = Board.create(),
                player1 = player1,
                player2 = player2,
                nextPlayerNumber = player1.number,
                progress = GameProgress.CREATED,
            )
            return game
        }

        fun recreate(
            gameId: GameId,
            board: Board,
            player1: Player,
            player2: Player,
            nextPlayerNumber: PlayerNumber,
            progress: GameProgress,
        ): Game {
            return Game(
                gameId = gameId,
                board = board,
                player1 = player1,
                player2 = player2,
                nextPlayerNumber = nextPlayerNumber,
                progress = progress,
            )
        }
    }

    fun getGameStatus(): GameState {
        return GameState(
            gameId,
            player1.name,
            player2.name,
            board.diskMap.getPlacedDiskMap(),
            nextPlayerNumber,
            progress,
        )
    }

    fun copy(
        board: Board = this.board,
        nextPlayerNumber: PlayerNumber = this.nextPlayerNumber,
        progress: GameProgress = this.progress,
    ): Game = Game(gameId, board, player1, player2, nextPlayerNumber, progress)

    fun putDisk(move: Move): Game {
        validateState(move)
        val nextBoard = board.putDisk(move)

        return this.getNextGame(nextBoard)
    }

    private fun validateState(move: Move) {
        if (this.progress == GameProgress.FINISHED) {
            throw GameAlreadyFinishedDomainException(
                message = "ディスクを置く事はできません",
                description = "ゲームが終了しているためディスクを配置することはできません。"
            )
        }
        if (move.number != nextPlayerNumber) {
            throw IllegalArgumentDomainException(
                message = "ディスクを置く事はできません",
                description = "自分の順番ではないプレイヤーが駒を置くことはできません。playerMove: $move, nextPlayerNumber: $nextPlayerNumber"
            )
        }
    }

    private fun getNextGame(nextBoard: Board): Game {
        val (currentPlayerNumber, nextPlayerNumber) = when (this.nextPlayerNumber) {
            PlayerNumber.PLAYER1 -> Pair(PlayerNumber.PLAYER1, PlayerNumber.PLAYER2)
            PlayerNumber.PLAYER2 -> Pair(PlayerNumber.PLAYER2, PlayerNumber.PLAYER1)
        }

        // 次に駒を置くプレイヤーが配置できるマスを取得し次手のゲーム状態を決定する
        val currentPlayersPuttableSquares = nextBoard.getAllPuttableSquares(currentPlayerNumber)
        val nextPlayersPuttableSquares = nextBoard.getAllPuttableSquares(nextPlayerNumber)
        return if (nextPlayersPuttableSquares.isEmpty() && currentPlayersPuttableSquares.isEmpty()) {
            this.copy(board = nextBoard, progress = GameProgress.FINISHED)
        } else if (nextPlayersPuttableSquares.isEmpty()) {
            this.copy(board = nextBoard, nextPlayerNumber = currentPlayerNumber, progress = GameProgress.IN_PROGRESS)
        } else {
            this.copy(board = nextBoard, nextPlayerNumber = nextPlayerNumber, progress = GameProgress.IN_PROGRESS)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Game) return false

        if (gameId != other.gameId) return false
        if (board != other.board) return false
        if (player1 != other.player1) return false
        if (player2 != other.player2) return false
        if (nextPlayerNumber != other.nextPlayerNumber) return false
        if (progress != other.progress) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gameId.hashCode()
        result = 31 * result + board.hashCode()
        result = 31 * result + player1.hashCode()
        result = 31 * result + player2.hashCode()
        result = 31 * result + nextPlayerNumber.hashCode()
        result = 31 * result + progress.hashCode()
        return result
    }

    override fun toString(): String {
        return "Game(gameId=$gameId, board=$board, player1=$player1, player2=$player2, nextPlayerNumber=$nextPlayerNumber, progress=$progress)"
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

data class GameState(
    val gameId: GameId,
    val player1Name: PlayerName,
    val player2Name: PlayerName,
    val diskMap: DiskMap,
    val nextPlayerNumber: PlayerNumber,
    val progress: GameProgress,
)

/**
 * ゲームの進行状態
 */
enum class GameProgress(val value: String) {
    CREATED("CREATED"), // ゲームが作成されて開始されるまでの状態
    IN_PROGRESS("IN_PROGRESS"), // ゲームが進行中の状態
    FINISHED("FINISHED"), // 勝敗が決まった状態
    ;

    companion object {
        fun of(value: String): GameProgress {
            return entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("GameProgressに存在しない値が指定されました。value=$value")
        }
    }
}