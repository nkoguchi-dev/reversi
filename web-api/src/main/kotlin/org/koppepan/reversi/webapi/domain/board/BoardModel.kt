package org.koppepan.reversi.webapi.domain.board

import org.koppepan.reversi.webapi.domain.player.Move
import org.koppepan.reversi.webapi.domain.player.PlayerNumber
import org.koppepan.reversi.webapi.domain.shared.ExceptionMessage
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
                    Position(x, y)
                }
            }
            return Board(DiskMap(positions.associateWith { null }))
                .putDiskWithoutAdjacentCheck(
                    Position(HorizontalPosition.D, VerticalPosition.FOUR),
                    Disk(DiskType.Light)
                )
                .putDiskWithoutAdjacentCheck(
                    Position(HorizontalPosition.E, VerticalPosition.FIVE),
                    Disk(DiskType.Light)
                )
                .putDiskWithoutAdjacentCheck(
                    Position(HorizontalPosition.D, VerticalPosition.FIVE),
                    Disk(DiskType.Dark)
                )
                .putDiskWithoutAdjacentCheck(
                    Position(HorizontalPosition.E, VerticalPosition.FOUR),
                    Disk(DiskType.Dark)
                )
        }
    }

    /**
     * 盤にディスクを置く
     */
    fun putDisk(move: Move): Board {
        validateDiskExist(move.position)
        val reversedDiskMap = getDisksToBeReversed(move.position, move.number.disk())
        requireOrThrow(reversedDiskMap.value.isNotEmpty()) {
            ExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "相手のディスクを裏返す事ができない位置にディスクを置くことはできません。${move}"
            )
        }
        return this.putDiskWithoutAdjacentCheck(move.position, move.number.disk())
            .reverseDisks(reversedDiskMap)
    }

    /**
     * 指定の位置に置かれているディスクを取得する
     */
    fun getDisk(position: Position): Disk? {
        return diskMap.getDisk(position)
    }

    /**
     * 指定のプレイヤーが置ける全てのマスを取得する
     */
    fun getAllPuttableSquares(playerNumber: PlayerNumber): List<Position> {
        return diskMap
            .getAllEmptySquares()
            .filter { position -> getDisksToBeReversed(position, Disk(playerNumber.diskType)).value.isNotEmpty() }
            .sortedBy { it.x.value + it.y.value }
    }

    private fun copy(diskMap: DiskMap): Board {
        return Board(diskMap)
    }

    /**
     * 裏返す事が可能な相手の駒があるか？のチェックを行わずに盤に駒を置く
     */
    private fun putDiskWithoutAdjacentCheck(position: Position, disk: Disk?): Board {
        validateDiskExist(position)
        return Board(diskMap.putDisk(position, disk))
    }

    private fun reverseDisks(reversedDiskMap: DiskMap): Board {
        return this.copy(diskMap = diskMap.reverseDisks(reversedDiskMap))
    }

    /**
     * 指定のPositionにすでにディスクが置かれているかどうかチェックする
     */
    private fun validateDiskExist(position: Position) {
        requireOrThrow(diskMap.getDisk(position) == null) {
            ExceptionMessage(
                message = "ディスクを置く事はできません",
                description = "既にディスクが置かれている位置にディスクを置くことはできません。position: $position"
            )
        }
    }

    /**
     * 指定の位置にディスクを置いた場合に裏返るディスクを取得する
     */
    private fun getDisksToBeReversed(position: Position, disk: Disk): DiskMap {
        val lines = SquareLine.createFromPosition(position, diskMap)
        val map = lines.map {
            it.getReversibleDisks(position, disk)
        }.reduce { acc, map -> acc + map }
        return DiskMap(map)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Board) return false

        if (diskMap != other.diskMap) return false

        return true
    }

    override fun hashCode(): Int {
        return diskMap.hashCode()
    }
}

@JvmInline
value class DiskMap(
    val value: Map<Position, Disk?>,
) {
    companion object {
        fun of(vararg pairs: Pair<Position, Disk?>): DiskMap {
            return DiskMap(pairs.toMap())
        }
    }

    /**
     * 指定位置にあるディスクを取得する
     */
    fun getDisk(position: Position): Disk? {
        return value[position]
    }

    /**
     * 指定位置にディスクを置く
     */
    fun putDisk(position: Position, disk: Disk?): DiskMap {
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

    /**
     * 全てのディスクが置かれいるマスを取得する
     */
    fun getPlacedDiskMap(): DiskMap =
        DiskMap(value.filterValues { it != null })

    /**
     * 全ての空いているマスを取得する
     */
    fun getAllEmptySquares(): List<Position> =
        value.filterValues { it == null }.keys.toList()
}