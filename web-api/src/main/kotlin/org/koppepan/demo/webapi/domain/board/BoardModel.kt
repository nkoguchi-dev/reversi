package org.koppepan.demo.webapi.domain.board

import org.koppepan.demo.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.demo.webapi.domain.shared.requireOrThrow

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
            val initialDiskMap: MutableMap<SquarePosition, Disk?> = positions
                .associateWith { null }
                .toMutableMap()
            initialDiskMap[SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR)] = Disk(DiskType.Light)
            initialDiskMap[SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FIVE)] = Disk(DiskType.Light)
            initialDiskMap[SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FIVE)] = Disk(DiskType.Dark)
            initialDiskMap[SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FOUR)] = Disk(DiskType.Dark)
            return Board(initialDiskMap.toMap())
        }
    }

    fun putDisk(position: SquarePosition, disk: Disk?): Board {
        requireOrThrow(diskMap[position] == null) {
            CustomExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "既に${position}にディスクが置かれています"
            )
        }
        val adjacentPositions = position.getAdjacentPositions()
        requireOrThrow(adjacentPositions.any { diskMap[it] == disk?.reverse()}) {
            CustomExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "隣り合う場所に相手のディスクがありません。position: $position"
            )
        }
        return Board(diskMap + (position to disk))
    }

    fun getDisk(position: SquarePosition): Disk? {
        return diskMap[position]
    }
}