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
            val horizontalPositions = listOf(HorizontalPosition.A, HorizontalPosition.B, HorizontalPosition.C, HorizontalPosition.D, HorizontalPosition.E, HorizontalPosition.F, HorizontalPosition.G, HorizontalPosition.H)
            val squares = horizontalPositions.map { Square.create(SquarePosition(it, VerticalPosition.ONE), null) }
            assertDoesNotThrow {
                SquareLine.SquareLineHorizontal.create(squares)
            }
        }

        @Test
        @DisplayName("座標を指定してSquareLineHorizontalのインスタンスを生成できること")
        fun testSquareLineHorizontalFromPosition() {
            val square = assertDoesNotThrow<SquareLine.SquareLineHorizontal> {
                SquareLine.SquareLineHorizontal.createFromPosition(SquarePosition(HorizontalPosition.A, VerticalPosition.TWO))
            }
            assertAll(
                { assertEquals(VerticalPosition.TWO, square.squares[0].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[1].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[2].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[3].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[4].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[5].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[6].position.y) },
                { assertEquals(VerticalPosition.TWO, square.squares[7].position.y) },
            )
        }

        @Test
        @DisplayName("SquareLineHorizontalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineHorizontalSize() {
            val horizontalPositions = listOf(HorizontalPosition.A, HorizontalPosition.B, HorizontalPosition.C, HorizontalPosition.D, HorizontalPosition.E, HorizontalPosition.F, HorizontalPosition.G)
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
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.D, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.E, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.ONE), null),
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
            val squares = verticalPositions.map { Square.create(SquarePosition( HorizontalPosition.A, it), null) }
            assertDoesNotThrow {
                SquareLine.SquareLineVertical.create(squares)
            }
        }

        @Test
        @DisplayName("座標を指定してSquareLineVerticalのインスタンスを生成できること")
        fun testSquareLineVerticalFromPosition() {
            val square = assertDoesNotThrow<SquareLine.SquareLineVertical> {
                SquareLine.SquareLineVertical.createFromPosition(SquarePosition(HorizontalPosition.B, VerticalPosition.ONE))
            }
            assertAll(
                { assertEquals(HorizontalPosition.B, square.squares[0].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[1].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[2].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[3].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[4].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[5].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[6].position.x) },
                { assertEquals(HorizontalPosition.B, square.squares[7].position.x) },
            )
        }


        @Test
        @DisplayName("SquareLineVerticalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineVerticalSize() {
            val verticalPositions = listOf(VerticalPosition.ONE, VerticalPosition.TWO, VerticalPosition.THREE, VerticalPosition.FOUR, VerticalPosition.FIVE, VerticalPosition.SIX, VerticalPosition.SEVEN)
            val squares = verticalPositions.map { Square.create(SquarePosition(HorizontalPosition.A, it), null) }
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
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.EIGHT), null),
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
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.EIGHT), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

        @Test
        @DisplayName("座標を指定してSquareLineDiagonalのインスタンスを生成できること")
        fun testSquareLineDiagonalFromPosition() {
            val squares = assertDoesNotThrow<List<SquareLine.SquareLineDiagonal>> {
                SquareLine.SquareLineDiagonal.createLines(SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR))
            }
            assertAll(
                { assertEquals(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), squares[0].squares[0].position) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalの配列順序が逆順でもインスタンスを生成できること")
        fun testSquareLineDiagonalReverse() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.EIGHT), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.ONE), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

            @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree2() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.THREE), null),
            )
            assertDoesNotThrow {
                SquareLine.SquareLineDiagonal.create(squares)
            }
        }

            @Test
        @DisplayName("SquareLineDiagonalの盤目の端から端に到達していないインスタンスを生成した場合例外が発生すること")
        fun testSquareLineDiagonalNotReach() {
            val squares = listOf(
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.THREE), null),
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
                Square.create(SquarePosition(HorizontalPosition.A, VerticalPosition.ONE), null),
                Square.create(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO), null),
                Square.create(SquarePosition(HorizontalPosition.C, VerticalPosition.THREE), null),
                Square.create(SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR), null),
                Square.create(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE), null),
                Square.create(SquarePosition(HorizontalPosition.F, VerticalPosition.SIX), null),
                Square.create(SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN), null),
                Square.create(SquarePosition(HorizontalPosition.H, VerticalPosition.SEVEN), null),
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