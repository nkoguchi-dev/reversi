package org.koppepan.reversi.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koppepan.reversi.webapi.domain.shared.CustomIllegalArgumentException

class SquareLineTest {
    @Nested
    @DisplayName("SquareLineHorizontalのテスト")
    inner class SquareLineHorizontalTest {
        @Test
        @DisplayName("SquareLineHorizontalのインスタンスを生成できること")
        fun testSquareLineHorizontal() {
            val horizontalPositions = listOf(HorizontalPosition.ONE, HorizontalPosition.TWO, HorizontalPosition.THREE, HorizontalPosition.FOUR, HorizontalPosition.FIVE, HorizontalPosition.SIX, HorizontalPosition.SEVEN, HorizontalPosition.EIGHT)
            val squares = horizontalPositions.map { Square.create(SquarePosition(it, VerticalPosition.ONE), null) }
            assertDoesNotThrow {
                SquareLine.SquareLineHorizontal.create(squares)
            }
        }

        @Test
        @DisplayName("SquareLineHorizontalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineHorizontalSize() {
            val horizontalPositions = listOf(HorizontalPosition.ONE, HorizontalPosition.TWO, HorizontalPosition.THREE, HorizontalPosition.FOUR, HorizontalPosition.FIVE, HorizontalPosition.SIX, HorizontalPosition.SEVEN)
            val squares = horizontalPositions.map { Square.create(SquarePosition(it, VerticalPosition.ONE), null) }
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineHorizontal.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineHorizontalは8個の要素を持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineHorizontalに設定するSquareに異なるy座標ある場合例外が発生すること")
        fun testSquareLineHorizontalY() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.TWO, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.THREE, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.SIX, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.ONE), null),
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
            val verticalPositions = listOf(VerticalPosition.ONE, VerticalPosition.TWO, VerticalPosition.THREE, VerticalPosition.FOUR, VerticalPosition.FIVE, VerticalPosition.SIX, VerticalPosition.SEVEN, VerticalPosition.EIGHT)
            val squares = verticalPositions.map { Square.create(SquarePosition( HorizontalPosition.ONE, it), null) }
            assertDoesNotThrow {
                SquareLine.SquareLineVertical.create(squares)
            }
        }

        @Test
        @DisplayName("SquareLineVerticalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineVerticalSize() {
            val verticalPositions = listOf(VerticalPosition.ONE, VerticalPosition.TWO, VerticalPosition.THREE, VerticalPosition.FOUR, VerticalPosition.FIVE, VerticalPosition.SIX, VerticalPosition.SEVEN)
            val squares = verticalPositions.map { Square.create(SquarePosition(HorizontalPosition.ONE, it), null) }
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineVertical.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineVerticalは8個の要素を持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineVerticalに設定するSquareに異なるx座標ある場合例外が発生すること")
        fun testSquareLineVerticalX() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.TWO, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.EIGHT), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineVertical.create(squares)
            }
            assertAll(
                {
                    assertEquals(
                        "SquareLineVerticalは全てのSquareが同じx座標を持つ必要があります",
                        exception.message
                    )
                },
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
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.TWO, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.THREE, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.SIX, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.EIGHT), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

        @Test
        @DisplayName("SquareLineDiagonalの配列順序が逆順でもインスタンスを生成できること")
        fun testSquareLineDiagonalReverse() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.EIGHT), null),
                Square.create(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.SIX, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.THREE, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.TWO, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.TWO, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.THREE, VerticalPosition.ONE), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

            @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree2() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.SIX, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.THREE), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

            @Test
        @DisplayName("SquareLineDiagonalの盤目の端から端に到達していないインスタンスを生成した場合例外が発生すること")
        fun testSquareLineDiagonalNotReach() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.TWO, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.THREE, VerticalPosition.THREE), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal.create(squares)
            }
            assertAll(
                {
                    assertEquals(
                        "SquareLineDiagonalはBoardの端から端に並ぶ要素を持つ必要があります",
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
                { assertEquals("SquareLineDiagonalは3つ以上の要素を持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareのx座標とy座標が不正の場合例外が発生すること")
        fun testSquareLineDiagonalXY() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.TWO, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.THREE, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.SIX, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.SEVEN), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal.create(squares)
            }
            assertAll(
                { assertEquals("SquareLineDiagonalは斜めの要素を持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }
    }
}