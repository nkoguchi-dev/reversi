package org.koppepan.reversi.webapi.domain.board

/**
 * マス目の座標を表すクラス
 */
class SquarePosition(
    val x: HorizontalPosition,
    val y: VerticalPosition,
) {
    fun getAdjacentPositions(): List<SquarePosition> {
        val adjacentPositions = mutableListOf<SquarePosition>()
        val prevX = x.prev()
        val nextX = x.next()
        val prevY = y.prev()
        val nextY = y.next()
        prevX?.let { px ->
            prevY?.let { py ->
                adjacentPositions.add(SquarePosition(px, py))
            }
            adjacentPositions.add(SquarePosition(px, y))
            nextY?.let { ny ->
                adjacentPositions.add(SquarePosition(px, ny))
            }
        }
        prevY?.let { py ->
            adjacentPositions.add(SquarePosition(x, py))
        }
        nextY?.let { ny ->
            adjacentPositions.add(SquarePosition(x, ny))
        }
        nextX?.let { nx ->
            prevY?.let { py ->
                adjacentPositions.add(SquarePosition(nx, py))
            }
            adjacentPositions.add(SquarePosition(nx, y))
            nextY?.let { ny ->
                adjacentPositions.add(SquarePosition(nx, ny))
            }
        }
        return adjacentPositions.toList()
    }

    fun copy(
        x: HorizontalPosition = this.x,
        y: VerticalPosition = this.y
    ): SquarePosition {
        return SquarePosition(x, y)
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
        var result = x.value
        result = 31 * result + y.value
        return result
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}

enum class HorizontalPosition(val value: Int, val prev: Int?, val next: Int?) {
    A(1, null, 2),
    B(2, 1, 3),
    C(3, 2, 4),
    D(4, 3, 5),
    E(5, 4, 6),
    F(6, 5, 7),
    G(7, 6, 8),
    H(8, 7, null),
    ;

    companion object {
        fun valueOf(value: Int?): HorizontalPosition? {
            return HorizontalPosition.entries.find { it.value == value }
        }
    }

    fun prev(): HorizontalPosition? {
        return prev?.let { HorizontalPosition.valueOf(it) }
    }

    fun next(): HorizontalPosition? {
        return next?.let { HorizontalPosition.valueOf(it) }
    }
}

enum class VerticalPosition(val value: Int, val prev: Int?, val next: Int?) {
    ONE(1, null, 2),
    TWO(2, 1, 3),
    THREE(3, 2, 4),
    FOUR(4, 3, 5),
    FIVE(5, 4, 6),
    SIX(6, 5, 7),
    SEVEN(7, 6, 8),
    EIGHT(8, 7, null),
    ;

    companion object {
        fun valueOf(value: Int?): VerticalPosition? {
            return VerticalPosition.entries.find { it.value == value }
        }
    }

    fun prev(): VerticalPosition? {
        return prev?.let { VerticalPosition.valueOf(it) }
    }

    fun next(): VerticalPosition? {
        return next?.let { VerticalPosition.valueOf(it) }
    }
}