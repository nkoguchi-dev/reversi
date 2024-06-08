package org.koppepan.reversi.webapi.domain.board

import org.koppepan.reversi.webapi.domain.shared.ExceptionMessage
import org.koppepan.reversi.webapi.domain.shared.requireOrThrow

/**
 * 盤のマス目１つを表すクラス
 */
class Square private constructor(
    val position: Position,
    val disk: Disk?,
) {
    companion object {
        fun create(position: Position, disk: Disk?): Square = Square(position, disk)
    }

    private fun copy(
        position: Position = this.position,
        disk: Disk? = this.disk
    ): Square {
        requireOrThrow(position == this.position) {
            ExceptionMessage(
                message = "Squareのpositionは変更できません",
                description = "",
            )
        }
        requireOrThrow(this.disk == null || disk != null) {
            ExceptionMessage(
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