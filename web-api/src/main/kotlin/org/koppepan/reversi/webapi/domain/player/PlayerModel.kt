package org.koppepan.reversi.webapi.domain.player

import org.koppepan.reversi.webapi.domain.board.DiskType
import org.koppepan.reversi.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.reversi.webapi.domain.shared.requireOrThrow

class Player private constructor(
    val name: PlayerName,
    val diskType: DiskType,
) {
    companion object {
        fun create(name: PlayerName, diskType: DiskType): Player {
            return Player(name, diskType)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Player) return false

        if (name != other.name) return false
        if (diskType != other.diskType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + diskType.hashCode()
        return result
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