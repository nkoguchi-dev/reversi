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
                    SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR),
                    Disk(DiskType.Light)
                )
                .putDiskWithoutAdjacentCheck(
                    SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FIVE),
                    Disk(DiskType.Light)
                )
                .putDiskWithoutAdjacentCheck(
                    SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FIVE),
                    Disk(DiskType.Dark)
                )
                .putDiskWithoutAdjacentCheck(
                    SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FOUR),
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

    fun putDisk(playerMove: PlayerMove): Board {
        validatePosition(playerMove.position)
        val adjacentPositions = playerMove.position.getAdjacentPositions()
        requireOrThrow(adjacentPositions.any { diskMap[it] == playerMove.disk.reverse() }) {
            CustomExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "隣り合う場所に相手のディスクがありません。position: ${playerMove.position}"
            )
        }
        return Board(diskMap + (playerMove.position to playerMove.disk))
    }

    fun getDisk(position: SquarePosition): Disk? {
        return diskMap[position]
    }

    // 裏返す事が可能な相手の駒があるか？のチェックを行わずに盤に駒を置く
    private fun putDiskWithoutAdjacentCheck(position: SquarePosition, disk: Disk?): Board {
        validatePosition(position)
        return Board(diskMap + (position to disk))
    }

    // 指定のPositionにすでにディスクが置かれているかどうかチェックする
    private fun validatePosition(position: SquarePosition) {
        requireOrThrow(diskMap[position] == null) {
            CustomExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "既に${position}にディスクが置かれています"
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