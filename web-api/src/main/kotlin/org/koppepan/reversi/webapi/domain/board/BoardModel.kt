package org.koppepan.reversi.webapi.domain.board

import org.koppepan.reversi.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.reversi.webapi.domain.shared.requireOrThrow

/**
 * 盤を表すクラス
 */
class Board private constructor(
    val diskMap: Map<SquarePosition, Disk?>,
) {
    companion object {
        fun create(): Board {
            val positions = HorizontalPosition.entries.flatMap { x ->
                VerticalPosition.entries.map { y ->
                    SquarePosition(x, y)
                }
            }
            return Board(positions.associateWith { null })
                .putDiskWithoutAdjacentCheck(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                    Disk(DiskType.Light)
                )
                .putDiskWithoutAdjacentCheck(
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE),
                    Disk(DiskType.Light)
                )
                .putDiskWithoutAdjacentCheck(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FIVE),
                    Disk(DiskType.Dark)
                )
                .putDiskWithoutAdjacentCheck(
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FOUR),
                    Disk(DiskType.Dark)
                )
        }

        fun recreate(histories: List<PlayerMove>): Board {
            return histories.fold(create()) { board, playerMove ->
                board.putDisk(playerMove)
            }
        }

        fun mappedInitialize(diskMap: Map<SquarePosition, Disk?>): Board {
            return Board(diskMap)
        }
    }

    /**
     * 盤にディスクを置く
     */
    fun putDisk(playerMove: PlayerMove): Board {
        validateDiskExist(playerMove.position)
        validateCanReverse(playerMove.position, playerMove.disk)
        return Board(diskMap + (playerMove.position to playerMove.disk))
    }

    /**
     * 指定の位置に置かれているディスクを取得する
     */
    fun getDisk(position: SquarePosition): Disk? {
        return diskMap[position]
    }

    /**
     * 裏返す事が可能な相手の駒があるか？のチェックを行わずに盤に駒を置く
     */
    private fun putDiskWithoutAdjacentCheck(position: SquarePosition, disk: Disk?): Board {
        validateDiskExist(position)
        return Board(diskMap + (position to disk))
    }

    /**
     * 指定のPositionにすでにディスクが置かれているかどうかチェックする
     */
    private fun validateDiskExist(position: SquarePosition) {
        requireOrThrow(diskMap[position] == null) {
            CustomExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "既にディスクが置かれている位置にディスクを置くことはできません。position: $position"
            )
        }
    }

    /**
     * 指定の位置にディスクを置いた場合に裏返す事ができるかどうかをチェックする
     */
    private fun validateCanReverse(position: SquarePosition, disk: Disk) {
        val lines = SquareLine.createFromPosition(position)
        requireOrThrow(lines.any { it.canReverse(position, disk) }) {
            CustomExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "相手のディスクを裏返す事ができない位置にディスクを置くことはできません。position: $position"
            )
        }
    }
}

class PlayerMove private constructor(
    val position: SquarePosition,
    val disk: Disk,
) {
    companion object {
        fun create(position: SquarePosition, disk: Disk): PlayerMove {
            return PlayerMove(position, disk)
        }
    }
}