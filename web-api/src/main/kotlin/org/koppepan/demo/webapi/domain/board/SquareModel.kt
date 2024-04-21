package org.koppepan.demo.webapi.domain.board

import org.koppepan.demo.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.demo.webapi.domain.shared.requireOrThrow

data class Square(
    val position: SquarePosition,
    val disk: Disk?,
) {
    fun putDisk(disk: Disk): Square = this.copy(disk = disk)

    fun reverseDisk(): Square = this.disk?.let { this.copy(disk = it.reverse()) } ?: this
}

data class SquarePosition(
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
}

sealed interface SquareLine {
    val squares: List<Square>
    val type: SquareLineType

    data class SquareLineHorizontal(
        override val squares: List<Square>,
        override val type: SquareLineType = SquareLineType.Horizontal,
    ) : SquareLine {
        init {
            requireOrThrow(squares.size == 8) {
                CustomExceptionMessage(
                    message = "SquareLineHorizontalは8つのSquareを持つ必要があります",
                    description = "",
                )
            }
            requireOrThrow(squares.all { it.position.y == squares.first().position.y }) {
                CustomExceptionMessage(
                    message = "SquareLineHorizontalは全てのSquareが同じy座標を持つ必要があります",
                    description = "",
                )
            }
        }
    }

    data class SquareLineVertical(
        override val squares: List<Square>,
        override val type: SquareLineType = SquareLineType.Vertical,
    ) : SquareLine {
        init {
            requireOrThrow(squares.size == 8) {
                CustomExceptionMessage(
                    message = "SquareLineVerticalは8つのSquareを持つ必要があります",
                    description = "",
                )
            }
            requireOrThrow(squares.all { it.position.x == squares.first().position.x }) {
                CustomExceptionMessage(
                    message = "SquareLineVerticalは全てのSquareが同じx座標を持つ必要があります",
                    description = "",
                )
            }
        }
    }

    data class SquareLineDiagonal(
        val squaresArgument: List<Square>,
        override val type: SquareLineType = SquareLineType.Diagonal,
    ) : SquareLine {
        // チェック処理でソートを省略するためにコンストラクタでソートを行う
        override val squares: List<Square> = squaresArgument.sortedBy { it.position.x }

        init {
            // マスが3つ以下の斜めラインは反転チェックをする必要がないため除外
            requireOrThrow(squares.size >= 3) {
                CustomExceptionMessage(
                    message = "SquareLineDiagonalは3つ以上のSquareを持つ必要があります",
                    description = "",
                )
            }
            requireOrThrow(
                squares.zipWithNext().all { (prev, curr) ->
                        (curr.position.x - prev.position.x == 1) && (curr.position.y - prev.position.y == 1)
                    }
            ) {
                CustomExceptionMessage(
                    message = "SquareLineDiagonalは斜めのSquareを持つ必要があります",
                    description = "",
                )
            }
            requireOrThrow(
                (squares.first().position.x == 0 || squares.first().position.y == 0)
                        && (squares.last().position.x == 7 || squares.last().position.y == 7)
            ) {
                CustomExceptionMessage(
                    message = "SquareLineDiagonalはBoardの端から端に並ぶSquareを持つ必要があります",
                    description = "",
                )
            }
        }
    }
}

enum class SquareLineType {
    Horizontal,
    Vertical,
    Diagonal,
}