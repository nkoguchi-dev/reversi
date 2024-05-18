package org.koppepan.demo.webapi.domain.board

import org.koppepan.demo.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.demo.webapi.domain.shared.requireOrThrow

/**
 * マス目を列で管理するクラス
 */
sealed interface SquareLine {
    val squares: List<Square>
    val type: SquareLineType

    /**
     * 縦のラインを表すクラス
     */
    class SquareLineHorizontal private constructor(
        override val squares: List<Square>,
        override val type: SquareLineType = SquareLineType.Horizontal,
    ) : SquareLine {
        companion object {
            fun create(squares: List<Square>): SquareLineHorizontal {
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
                return SquareLineHorizontal(squares)
            }
        }
    }

    /**
     * 横のラインを表すクラス
     */
    class SquareLineVertical private constructor(
        override val squares: List<Square>,
        override val type: SquareLineType = SquareLineType.Vertical,
    ) : SquareLine {
        companion object {
            fun create(squares: List<Square>): SquareLineVertical {
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
                return SquareLineVertical(squares)
            }
        }
    }

    /**
     * 斜めのラインを表すクラス
     */
    class SquareLineDiagonal private constructor(
        override val squares: List<Square>,
        override val type: SquareLineType = SquareLineType.Diagonal,
    ) : SquareLine {
        companion object {
            fun create(squares: List<Square>): SquareLineDiagonal {
                // チェック処理でソートを省略するために引数を並べ替えてコピー
                val sortedSquares = squares.sortedBy { it.position.x }

                // マスが3つ以下の斜めラインは反転チェックをする必要がないため除外
                requireOrThrow(sortedSquares.size >= 3) {
                    CustomExceptionMessage(
                        message = "SquareLineDiagonalは3つ以上のSquareを持つ必要があります",
                        description = "",
                    )
                }
                requireOrThrow(
                    sortedSquares.zipWithNext().all { (prev, curr) ->
                        (curr.position.x - prev.position.x == 1) && (curr.position.y - prev.position.y == 1)
                    }
                ) {
                    CustomExceptionMessage(
                        message = "SquareLineDiagonalは斜めのSquareを持つ必要があります",
                        description = "",
                    )
                }
                requireOrThrow(
                    (sortedSquares.first().position.x == 0 || sortedSquares.first().position.y == 0)
                            && (sortedSquares.last().position.x == 7 || sortedSquares.last().position.y == 7)
                ) {
                    CustomExceptionMessage(
                        message = "SquareLineDiagonalはBoardの端から端に並ぶSquareを持つ必要があります",
                        description = "",
                    )
                }
                return SquareLineDiagonal(sortedSquares)
            }
        }
    }
}

enum class SquareLineType {
    Horizontal,
    Vertical,
    Diagonal,
}