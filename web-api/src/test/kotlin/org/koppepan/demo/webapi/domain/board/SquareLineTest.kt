package org.koppepan.demo.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koppepan.demo.webapi.domain.shared.CustomIllegalArgumentException

class SquareLineTest {
    @Nested
    @DisplayName("SquareLineHorizontalのテスト")
    inner class SquareLineHorizontalTest {
        @Test
        @DisplayName("SquareLineHorizontalのインスタンスを生成できること")
        fun testSquareLineHorizontal() {
            val squares = (0..7).map { Square.create(SquarePosition.create(it, 0), null) }
            val squareLine = SquareLine.SquareLineHorizontal.create(squares)
            assertAll(
                { assertEquals(8, squareLine.squares.size) },
                { assertEquals(SquareLineType.Horizontal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineHorizontalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineHorizontalSize() {
            val squares = (0..6).map { Square.create(SquarePosition.create(it, 0), null) }
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineHorizontal.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineHorizontalは8つのSquareを持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineHorizontalのSquareのy座標が不正の場合例外が発生すること")
        fun testSquareLineHorizontalY() {
            val squares = listOf(
                Square.create(SquarePosition.create(0, 0), null),
                Square.create(SquarePosition.create(1, 1), null),
                Square.create(SquarePosition.create(2, 0), null),
                Square.create(SquarePosition.create(3, 0), null),
                Square.create(SquarePosition.create(4, 0), null),
                Square.create(SquarePosition.create(5, 0), null),
                Square.create(SquarePosition.create(6, 0), null),
                Square.create(SquarePosition.create(7, 0), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineHorizontal.create(squares)
            }
            assertAll(
                {
                    assertEquals(
                        "SquareLineHorizontalは全てのSquareが同じy座標を持つ必要があります",
                        exception.message
                    )
                },
                { assertEquals("", exception.description) },
            )
        }
    }

    @Nested
    @DisplayName("SquareLineVerticalのテスト")
    inner class SquareLineVerticalTest {
        @Test
        @DisplayName("SquareLineVerticalのインスタンスを生成できること")
        fun testSquareLineVertical() {
            val squares = (0..7).map { Square.create(SquarePosition.create(0, it), null) }
            val squareLine = SquareLine.SquareLineVertical.create(squares)
            assertAll(
                { assertEquals(8, squareLine.squares.size) },
                { assertEquals(SquareLineType.Vertical, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineVerticalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineVerticalSize() {
            val squares = (0..6).map { Square.create(SquarePosition.create(0, it), null) }
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineVertical.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineVerticalは8つのSquareを持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineVerticalのSquareのx座標が不正の場合例外が発生すること")
        fun testSquareLineVerticalX() {
            val squares = listOf(
                Square.create(SquarePosition.create(0, 0), null),
                Square.create(SquarePosition.create(0, 1), null),
                Square.create(SquarePosition.create(1, 2), null),
                Square.create(SquarePosition.create(0, 3), null),
                Square.create(SquarePosition.create(0, 4), null),
                Square.create(SquarePosition.create(0, 5), null),
                Square.create(SquarePosition.create(0, 6), null),
                Square.create(SquarePosition.create(0, 7), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineVertical.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineVerticalは全てのSquareが同じx座標を持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }
    }

    @Nested
    @DisplayName("SquareLineDiagonalのテスト")
    inner class SquareLineDiagonal {
        @Test
        @DisplayName("SquareLineDiagonalのインスタンスを生成できること")
        fun testSquareLineDiagonal() {
            val squares = (0..7).map { Square.create(SquarePosition.create(it, it), null) }
            val squareLine = SquareLine.SquareLineDiagonal.create(squares)
            assertAll(
                { assertEquals(8, squareLine.squares.size) },
                { assertEquals(SquareLineType.Diagonal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalの配列順序が逆順でもインスタンスを生成できること")
        fun testSquareLineDiagonalReverse() {
            val squares = (0..7).map { Square.create(SquarePosition.create(7 - it, 7 - it), null) }
            val squareLine = SquareLine.SquareLineDiagonal.create(squares)
            assertAll(
                { assertEquals(8, squareLine.squares.size) },
                { assertEquals(SquareLineType.Diagonal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree() {
            val squares = listOf(
                Square.create(SquarePosition.create(0, 5), null),
                Square.create(SquarePosition.create(1, 6), null),
                Square.create(SquarePosition.create(2, 7), null),
            )
            val squareLine = SquareLine.SquareLineDiagonal.create(squares)
            assertAll(
                { assertEquals(3, squareLine.squares.size) },
                { assertEquals(SquareLineType.Diagonal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree2() {
            val squares = listOf(
                Square.create(SquarePosition.create(5, 0), null),
                Square.create(SquarePosition.create(6, 1), null),
                Square.create(SquarePosition.create(7, 2), null),
            )
            val squareLine = SquareLine.SquareLineDiagonal.create(squares)
            assertAll(
                { assertEquals(3, squareLine.squares.size) },
                { assertEquals(SquareLineType.Diagonal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalの盤目の端から端に到達していないインスタンスを生成した場合例外が発生すること")
        fun testSquareLineDiagonalNotReach() {
            val squares = listOf(
                Square.create(SquarePosition.create(0, 0), null),
                Square.create(SquarePosition.create(1, 1), null),
                Square.create(SquarePosition.create(2, 2), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal.create(squares)
            }
            assertAll(
                {
                    assertEquals(
                        "SquareLineDiagonalはBoardの端から端に並ぶSquareを持つ必要があります",
                        exception.message
                    )
                },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが空の場合例外が発生すること")
        fun testSquareLineDiagonalSize() {
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal.create(listOf())
            }
            assertAll(
                { assertEquals("SquareLineDiagonalは3つ以上のSquareを持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareのx座標とy座標が不正の場合例外が発生すること")
        fun testSquareLineDiagonalXY() {
            val squares = listOf(
                Square.create(SquarePosition.create(0, 0), null),
                Square.create(SquarePosition.create(1, 1), null),
                Square.create(SquarePosition.create(2, 2), null),
                Square.create(SquarePosition.create(3, 3), null),
                Square.create(SquarePosition.create(4, 4), null),
                Square.create(SquarePosition.create(5, 5), null),
                Square.create(SquarePosition.create(6, 6), null),
                Square.create(SquarePosition.create(7, 6), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineDiagonalは斜めのSquareを持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }
    }
}