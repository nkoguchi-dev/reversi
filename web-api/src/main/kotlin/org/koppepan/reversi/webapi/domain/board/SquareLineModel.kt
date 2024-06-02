package org.koppepan.reversi.webapi.domain.board

import org.koppepan.reversi.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.reversi.webapi.domain.shared.requireOrThrow


/**
 * マス目を列で管理するクラス
 */
sealed interface SquareLine {
    val squares: List<Square>

    /**
     * 指定した位置にディスクを置けるかどうかを返す
     */
    fun canReverse(position: SquarePosition, disk: Disk): Boolean

    companion object {
        /**
         * マス目の位置を含むラインのリストを生成する
         */
        fun createFromPosition(position: SquarePosition, diskMap: Map<SquarePosition, Disk?>): List<SquareLine> {
            return listOf(
                SquareLineHorizontal.createFromPosition(position, diskMap),
                SquareLineVertical.createFromPosition(position, diskMap),
            ) + SquareLineDiagonal.createLines(position, diskMap)
        }
    }

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

            fun createFromPosition(
                position: SquarePosition,
                diskMap: Map<SquarePosition, Disk?>
            ): SquareLineHorizontal {
                val squares = HorizontalPosition.entries.map { x ->
                    Square.create(position.copy(x = x), diskMap[SquarePosition(x, position.y)])
                }
                return create(squares)
            }
        }

        override fun canReverse(position: SquarePosition, disk: Disk): Boolean {
            // TODO: 未実装
            return true
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

            fun createFromPosition(position: SquarePosition, diskMap: Map<SquarePosition, Disk?>): SquareLineVertical {
                val squares = VerticalPosition.entries.map { y ->
                    Square.create(position.copy(y = y), diskMap[SquarePosition(position.x, y)])
                }
                return create(squares)
            }
        }

        override fun canReverse(position: SquarePosition, disk: Disk): Boolean {
            // TODO: 未実装
            return true
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

            fun createLines(position: SquarePosition, diskMap: Map<SquarePosition, Disk?>): List<SquareLineDiagonal> {
                return listOf(
                    createDiagonalLineFromTopLeftToBottomRight(position, diskMap),
                    createDiagonalLineFromBottomRightToTopLeft(position, diskMap)
                )
            }

            // 左上から右下に向かう斜めラインを作成
            private fun createDiagonalLineFromTopLeftToBottomRight(
                position: SquarePosition,
                diskMap: Map<SquarePosition, Disk?>
            ): SquareLineDiagonal {
                // 起点から左上に向かう斜めラインを生成
                val squaresTopLeft = generateSequence(position) { current ->
                    val prevX = current.x.prev()
                    val prevY = current.y.prev()
                    if (prevX != null && prevY != null) {
                        current.copy(x = prevX, y = prevY)
                    } else {
                        null
                    }
                }.fold(mutableListOf<Square>()) { acc, current ->
                    acc.apply {
                        add(Square.create(current, diskMap[SquarePosition(current.x, current.y)]))
                    }
                }
                // 起点から右下に向かう斜めラインを生成
                val squaresBottomRight = generateSequence(position) { current ->
                    val nextX = current.x.next()
                    val nextY = current.y.next()
                    if (nextX != null && nextY != null) {
                        current.copy(x = nextX, y = nextY)
                    } else {
                        null
                    }
                }.fold(mutableListOf<Square>()) { acc, current ->
                    acc.apply { add(Square.create(current, diskMap[SquarePosition(current.x, current.y)])) }
                }
                return create(
                    (squaresTopLeft + squaresBottomRight)
                        .distinctBy { it.position }
                        .sortedBy { it.position.x }
                )
            }

            // 右下から左上に向かう斜めラインを作成
            private fun createDiagonalLineFromBottomRightToTopLeft(
                position: SquarePosition,
                diskMap: Map<SquarePosition, Disk?>
            ): SquareLineDiagonal {
                // 起点から右上に向かう斜めラインを生成
                val squaresTopRight = generateSequence(position) { current ->
                    val nextX = current.x.next()
                    val prevY = current.y.prev()
                    if (nextX != null && prevY != null) {
                        current.copy(x = nextX, y = prevY)
                    } else {
                        null
                    }
                }.fold(mutableListOf<Square>()) { acc, current ->
                    acc.apply { add(Square.create(current, diskMap[SquarePosition(current.x, current.y)])) }
                }
                // 起点から左下に向かう斜めラインを生成
                val squaresBottomLeft = generateSequence(position) { current ->
                    val prevX = current.x.prev()
                    val nextY = current.y.next()
                    if (prevX != null && nextY != null) {
                        current.copy(x = prevX, y = nextY)
                    } else {
                        null
                    }
                }.fold(mutableListOf<Square>()) { acc, current ->
                    acc.apply { add(Square.create(current, diskMap[SquarePosition(current.x, current.y)])) }
                }
                return create(
                    (squaresTopRight + squaresBottomLeft)
                        .distinctBy { it.position }
                        .sortedBy { it.position.x }
                )
            }
        }

        override fun canReverse(position: SquarePosition, disk: Disk): Boolean {
            // TODO: 未実装
            return true
        }
    }
}