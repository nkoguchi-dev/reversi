package org.koppepan.reversi.webapi.domain.board

import org.koppepan.reversi.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.reversi.webapi.domain.shared.requireOrThrow


/**
 * マス目を列で管理するクラス
 */
sealed interface SquareLine {
    val squares: List<Square>
    /**
     * 縦のラインを表すクラス
     */
    class SquareLineHorizontal private constructor(
        override val squares: List<Square>,
    ) : SquareLine {
        companion object {
            fun create(squares: List<Square>): SquareLineHorizontal {
                requireOrThrow(squares.size == HorizontalPosition.entries.size) {
                    CustomExceptionMessage(
                        message = "SquareLineHorizontalは${HorizontalPosition.entries.size}個の要素を持つ必要があります",
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

            fun createFromPosition(position: SquarePosition): SquareLineHorizontal {
                val squares = HorizontalPosition.entries.map { x ->
                    Square.create(position.copy(x = x), null)
                }
                return create(squares)
            }
        }
    }

    /**
     * 横のラインを表すクラス
     */
    class SquareLineVertical private constructor(
        override val squares: List<Square>,
    ) : SquareLine {
        companion object {
            fun create(squares: List<Square>): SquareLineVertical {
                requireOrThrow(squares.size == VerticalPosition.entries.size) {
                    CustomExceptionMessage(
                        message = "SquareLineVerticalは${VerticalPosition.entries.size}個の要素を持つ必要があります",
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

            fun createFromPosition(position: SquarePosition): SquareLineVertical {
                val squares = VerticalPosition.entries.map { y ->
                    Square.create(position.copy(y = y), null)
                }
                return create(squares)
            }
        }
    }

    /**
     * 斜めのラインを表すクラス
     */
    class SquareLineDiagonal private constructor(
        override val squares: List<Square>,
    ) : SquareLine {
        companion object {
            fun create(squares: List<Square>): SquareLineDiagonal {
                // チェック処理でソートを省略するために引数を並べ替えてコピー
                val sortedSquares = squares.sortedBy { it.position.x }

                // マスが3つ以下の斜めラインは反転チェックをする必要がないため除外
                requireOrThrow(sortedSquares.size >= 3) {
                    CustomExceptionMessage(
                        message = "SquareLineDiagonalは3つ以上の要素を持つ必要があります",
                        description = "",
                    )
                }
                requireOrThrow(
                    sortedSquares.zipWithNext().all { (prev, curr) ->
                        (curr.position.x.prev() == prev.position.x) && (curr.position.y.prev() == prev.position.y)
                                || (curr.position.x.next() == prev.position.x) && (curr.position.y.next() == prev.position.y)
                                || (curr.position.x.prev() == prev.position.x) && (curr.position.y.next() == prev.position.y)
                                || (curr.position.x.next() == prev.position.x) && (curr.position.y.prev() == prev.position.y)
                    }
                ) {
                    CustomExceptionMessage(
                        message = "SquareLineDiagonalは斜めの要素を持つ必要があります",
                        description = "",
                    )
                }
                requireOrThrow(
                    // 最初のXが1で最後の最後のYが1or8
                    // 最初のXが1or8で最後の最後のYが8
                    (sortedSquares.first().position.x == HorizontalPosition.A && (sortedSquares.last().position.y == VerticalPosition.ONE || sortedSquares.last().position.y == VerticalPosition.EIGHT))
                            || (sortedSquares.last().position.x == HorizontalPosition.H && (sortedSquares.first().position.y == VerticalPosition.ONE || sortedSquares.first().position.y == VerticalPosition.EIGHT))
                ) {
                    CustomExceptionMessage(
                        message = "SquareLineDiagonalはBoardの端から端に並ぶ要素を持つ必要があります",
                        description = "",
                    )
                }
                return SquareLineDiagonal(sortedSquares)
            }

            fun createLines(position: SquarePosition): List<SquareLineDiagonal> {
                return listOf(
                    createDiagonalLineFromTopLeftToBottomRight(position),
                    createDiagonalLineFromBottomRightToTopLeft(position)
                )
            }

            // 左上から右下に向かう斜めラインを作成
            private fun createDiagonalLineFromTopLeftToBottomRight(position: SquarePosition): SquareLineDiagonal {
                val squares = mutableListOf<Square>()
                var current = position
                while (current.x.prev() != null && current.y.next() != null) {
                    squares.add(Square.create(current, null))
                    current = current.copy(x = current.x.prev()!!, y = current.y.next()!!)
                }
                while (current.x.next() != null && current.y.prev() != null) {
                    squares.add(Square.create(current, null))
                    current = current.copy(x = current.x.next()!!, y = current.y.prev()!!)
                }
                return create(squares.sortedBy { it.position.x })
            }

            // 右下から左上に向かう斜めラインを作成
            private fun createDiagonalLineFromBottomRightToTopLeft(position: SquarePosition): SquareLineDiagonal {
                val squares = mutableListOf<Square>()
                var current = position
                while (current.x.prev() != null && current.y.prev() != null) {
                    squares.add(Square.create(current, null))
                    current = current.copy(x = current.x.prev()!!, y = current.y.prev()!!)
                }
                while (current.x.next() != null && current.y.next() != null) {
                    squares.add(Square.create(current, null))
                    current = current.copy(x = current.x.next()!!, y = current.y.next()!!)
                }
                return create(squares.sortedBy { it.position.x })
            }
        }
    }
}