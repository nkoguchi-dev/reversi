package org.koppepan.reversi.webapi.domain.board

/**
 * マス目の座標を表すクラス
 */
class Position(
    val x: HorizontalPosition,
    val y: VerticalPosition,
) {
    fun getAdjacentPositions(): List<Position> {
        val adjacentPositions = mutableListOf<Position>()
        val prevX = x.prev()
        val nextX = x.next()
        val prevY = y.prev()
        val nextY = y.next()
        prevX?.let { px ->
            prevY?.let { py ->
                adjacentPositions.add(Position(px, py))
            }
            adjacentPositions.add(Position(px, y))
            nextY?.let { ny ->
                adjacentPositions.add(Position(px, ny))
            }
        }
        prevY?.let { py ->
            adjacentPositions.add(Position(x, py))
        }
        nextY?.let { ny ->
            adjacentPositions.add(Position(x, ny))
        }
        nextX?.let { nx ->
            prevY?.let { py ->
                adjacentPositions.add(Position(nx, py))
            }
            adjacentPositions.add(Position(nx, y))
            nextY?.let { ny ->
                adjacentPositions.add(Position(nx, ny))
            }
        }
        return adjacentPositions.toList()
    }

    fun copy(
        x: HorizontalPosition = this.x,
        y: VerticalPosition = this.y
    ): Position {
        return Position(x, y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Position) {
            return false
        }
        if (x != other.x || y != other.y) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}

enum class HorizontalPosition(val value: String, val prev: String?, val next: String?) {
    A("A", null, "B"),
    B("B", "A", "C"),
    C("C", "B", "D"),
    D("D", "C", "E"),
    E("E", "D", "F"),
    F("F", "E", "G"),
    G("G", "F", "H"),
    H("H", "G", null),
    ;

    companion object {
        fun of(value: String?): HorizontalPosition {
            return HorizontalPosition.entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("Invalid HorizontalPosition value: $value")
        }
    }

    fun prev(): HorizontalPosition? {
        return prev?.let { HorizontalPosition.of(it) }
    }

    fun next(): HorizontalPosition? {
        return next?.let { HorizontalPosition.of(it) }
    }
}

enum class VerticalPosition(val value: String, val prev: String?, val next: String?) {
    ONE("1", null, "2"),
    TWO("2", "1", "3"),
    THREE("3", "2", "4"),
    FOUR("4", "3", "5"),
    FIVE("5", "4", "6"),
    SIX("6", "5", "7"),
    SEVEN("7", "6", "8"),
    EIGHT("8", "7", null),
    ;

    companion object {
        fun of(value: String?): VerticalPosition {
            return VerticalPosition.entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("Invalid VerticalPosition value: $value")
        }
    }

    fun prev(): VerticalPosition? {
        return prev?.let { VerticalPosition.of(it) }
    }

    fun next(): VerticalPosition? {
        return next?.let { VerticalPosition.of(it) }
    }
}