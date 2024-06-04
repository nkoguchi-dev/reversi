package org.koppepan.reversi.webapi.domain.board

import org.koppepan.reversi.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.reversi.webapi.domain.shared.requireOrThrow

/**
 * 盤を表すクラス
 */
class Board private constructor(
    val diskMap: DiskMap,
) {
    companion object {
        fun create(): Board {
            val positions = HorizontalPosition.entries.flatMap { x ->
                VerticalPosition.entries.map { y ->
                    SquarePosition(x, y)
                }
            }
            return Board(DiskMap(positions.associateWith { null }))
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
    }

    fun copy(diskMap: DiskMap): Board {
        return Board(diskMap)
    }

    /**
     * 盤にディスクを置く
     */
    fun putDisk(playerMove: PlayerMove): Board {
        validateDiskExist(playerMove.position)
        val reversedDiskMap = getDisksToBeReversed(playerMove.position, playerMove.disk)
        requireOrThrow(reversedDiskMap.value.isNotEmpty()) {
            CustomExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "相手のディスクを裏返す事ができない位置にディスクを置くことはできません。${playerMove}"
            )
        }
        return this.putDiskWithoutAdjacentCheck(playerMove.position, playerMove.disk)
            .reverseDisks(reversedDiskMap)
    }

    /**
     * 指定の位置に置かれているディスクを取得する
     */
    fun getDisk(position: SquarePosition): Disk? {
        return diskMap.getDisk(position)
    }

    /**
     * 裏返す事が可能な相手の駒があるか？のチェックを行わずに盤に駒を置く
     */
    private fun putDiskWithoutAdjacentCheck(position: SquarePosition, disk: Disk?): Board {
        validateDiskExist(position)
        return Board(diskMap.putDisk(position, disk))
    }

    private fun reverseDisks(reversedDiskMap: DiskMap): Board {
        return this.copy(diskMap = diskMap.reverseDisks(reversedDiskMap))
    }

    /**
     * 指定のPositionにすでにディスクが置かれているかどうかチェックする
     */
    private fun validateDiskExist(position: SquarePosition) {
        requireOrThrow(diskMap.getDisk(position) == null) {
            CustomExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "既にディスクが置かれている位置にディスクを置くことはできません。position: $position"
            )
        }
    }

    /**
     * 指定の位置にディスクを置いた場合に裏返るディスクを取得する
     */
    private fun getDisksToBeReversed(position: SquarePosition, disk: Disk): DiskMap {
        val lines = SquareLine.createFromPosition(position, diskMap)
        val map = lines.map {
            it.getReversibleDisks(position, disk)
        }.reduce { acc, map -> acc + map }
        return DiskMap(map)
    }
}

@JvmInline
value class DiskMap(
    val value: Map<SquarePosition, Disk?>,
) {
    companion object {
        fun of(vararg pairs: Pair<SquarePosition, Disk?>): DiskMap {
            return DiskMap(pairs.toMap())
        }
    }

    /**
     * 指定位置にあるディスクを取得する
     */
    fun getDisk(position: SquarePosition): Disk? {
        return value[position]
    }

    /**
     * 指定位置にディスクを置く
     */
    fun putDisk(position: SquarePosition, disk: Disk?): DiskMap {
        val newDiskMap = value.toMutableMap()
        newDiskMap[position] = disk
        return DiskMap(newDiskMap)
    }

    /**
     * 指定位置にあるディスクを裏返す
     * @param toBeReversedDiskMap 裏返すディスクの位置とディスクのMap
     */
    fun reverseDisks(toBeReversedDiskMap: DiskMap): DiskMap {
        val newDiskMap = value.toMutableMap()
        toBeReversedDiskMap.value.forEach { (position, disk) ->
            newDiskMap[position] = disk?.reverse()
        }
        return DiskMap(newDiskMap)
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

    override fun toString(): String {
        return "PlayerMove(position=$position, disk=$disk)"
    }
}