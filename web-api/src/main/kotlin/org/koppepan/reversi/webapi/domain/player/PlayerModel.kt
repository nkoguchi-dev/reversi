package org.koppepan.reversi.webapi.domain.player

import org.koppepan.reversi.webapi.domain.board.Disk
import org.koppepan.reversi.webapi.domain.board.DiskType
import org.koppepan.reversi.webapi.domain.board.PlayerMove
import org.koppepan.reversi.webapi.domain.board.SquarePosition
import org.koppepan.reversi.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.reversi.webapi.domain.shared.requireOrThrow

class Player private constructor(
    val name: PlayerName,
    val diskType: DiskType,
    val number: PlayerNumber,
) {
    companion object {
        // 先手が黒で後手が白なのはReversiのルール
        fun createPlayer1(name: PlayerName): Player =
            Player(name, DiskType.Dark, PlayerNumber.PLAYER1)

        fun createPlayer2(name: PlayerName): Player =
            Player(name, DiskType.Light, PlayerNumber.PLAYER2)
    }

    /**
     * プレイヤーの行動を作成する
     */
    fun createMove(position: SquarePosition): PlayerMove {
        return PlayerMove(number, position, Disk(diskType))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Player) return false

        if (name != other.name) return false
        if (diskType != other.diskType) return false
        if (number != other.number) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + diskType.hashCode()
        result = 31 * result + number.hashCode()
        return result
    }

    override fun toString(): String {
        return "Player(name=$name, diskType=$diskType, playerNumber=$number)"
    }
}

@JvmInline
value class PlayerName (val value: String) {
    companion object {
        private const val MAX_LENGTH = 200
    }

    init {
        requireOrThrow(value.isNotBlank()) {
            CustomExceptionMessage(
                message = "プレイヤー名を作成する事ができません",
                description = "プレイヤー名は必ず指定してください"
            )
        }
        requireOrThrow(value.length <= MAX_LENGTH) {
            CustomExceptionMessage(
                message = "プレイヤー名を作成する事ができません",
                description = "プレイヤー名は${MAX_LENGTH}文字以内で指定してください"
            )
        }
    }
}

enum class PlayerNumber(val diskType: DiskType) {
    PLAYER1(DiskType.Dark),
    PLAYER2(DiskType.Light),
}