package org.koppepan.demo.webapi.domain.board

import org.koppepan.demo.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.demo.webapi.domain.shared.requireOrThrow

/**
 * 盤のマス目１つを表すクラス
 */
class Square private constructor(
    val position: SquarePosition,
    val disk: Disk?,
) {
    companion object {
        fun create(position: SquarePosition, disk: Disk?): Square = Square(position, disk)
    }

    private fun copy(
        position: SquarePosition = this.position,
        disk: Disk? = this.disk
    ): Square {
        requireOrThrow(position == this.position) {
            CustomExceptionMessage(
                message = "Squareのpositionは変更できません",
                description = "",
            )
        }
        requireOrThrow(this.disk == null || disk != null) {
            CustomExceptionMessage(
                message = "Squareからdiskを取り除く事はできません",
                description = "",
            )
        }
        return create(position, disk)
    }

    /**
     * SquareにDiskを置く
     */
    fun putDisk(disk: Disk): Square = this.copy(disk = disk)

    /**
     * SquareのDiskを裏返す
     */
    fun reverseDisk(): Square = this.disk?.let { this.copy(disk = it.reverse()) } ?: this
}

/**
 * マス目の座標を表すクラス
 */
class SquarePosition private constructor(
    val x: Int,
    val y: Int,
) {
    init {
        requireOrThrow(x in 0..7) {
            CustomExceptionMessage(
                message = "SquarePositionのxは0から7の範囲で設定してください",
                description = "",
            )
        }
        requireOrThrow(y in 0..7) {
            CustomExceptionMessage(
                message = "SquarePositionのyは0から7の範囲で設定してください",
                description = "",
            )
        }
    }

    companion object {
        fun create(x: Int, y: Int): SquarePosition = SquarePosition(x, y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is SquarePosition) {
            return false
        }
        if (x != other.x || y != other.y) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}