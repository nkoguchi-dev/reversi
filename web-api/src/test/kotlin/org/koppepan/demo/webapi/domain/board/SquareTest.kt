package org.koppepan.demo.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koppepan.demo.webapi.domain.shared.CustomIllegalArgumentException

class SquareTest {
    @Nested
    @DisplayName("Squareのテスト")
    inner class SquareTest {
        @Test
        @DisplayName("Squareのインスタンスを生成できること")
        fun testSquare() {
            val square = Square(
                position = SquarePosition(0, 0),
                disk = null,
            )
            assertAll(
                { assertEquals(0, square.position.x) },
                { assertEquals(0, square.position.y) },
                { assertNull(square.disk) },
            )
        }

        @Test
        @DisplayName("Squareにdiskを配置できること")
        fun testPutDisk() {
            val square = Square(
                position = SquarePosition(0, 0),
                disk = null,
            )
            val disk = Disk(DiskType.Light)
            val newSquare = square.putDisk(disk)
            assertAll(
                { assertEquals(0, newSquare.position.x) },
                { assertEquals(0, newSquare.position.y) },
                { assertEquals(disk, newSquare.disk) },
            )
        }

        @Test
        @DisplayName("Squareに配置したDiskを白から黒に反転できること")
        fun testReverseDiskToDark() {
            val square = Square(
                position = SquarePosition(0, 0),
                disk = Disk(DiskType.Light),
            )
            val newSquare = square.reverseDisk()
            assertAll(
                { assertEquals(0, newSquare.position.x) },
                { assertEquals(0, newSquare.position.y) },
                { assertEquals(DiskType.Dark, newSquare.disk?.diskType) },
            )
        }

        @Test
        @DisplayName("Squareに配置したDiskを黒から白に反転できること")
        fun testReverseDiskToLight() {
            val square = Square(
                position = SquarePosition(0, 0),
                disk = Disk(DiskType.Dark),
            )
            val newSquare = square.reverseDisk()
            assertAll(
                { assertEquals(0, newSquare.position.x) },
                { assertEquals(0, newSquare.position.y) },
                { assertEquals(DiskType.Light, newSquare.disk?.diskType) },
            )
        }
    }

    @Nested
    @DisplayName("SquarePositionのテスト")
    inner class SquarePositionTest {
        @Test
        @DisplayName("水平方向の位置が上限を超えたSquarePositionのインスタンスを生成すると例外が発生すること")
        fun testSquarePositionXOverFlow() {
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquarePosition(8, 0)
            }
            assertAll(
                { assertEquals("SquarePositionのxは0から7の範囲で設定してください", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("水平方向の位置がマイナスのSquarePositionのインスタンスを生成すると例外が発生すること")
        fun testSquarePositionXUnderFlow() {
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquarePosition(-1, 0)
            }
            assertAll(
                { assertEquals("SquarePositionのxは0から7の範囲で設定してください", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("垂直方向の位置が上限を超えたSquarePositionのインスタンスを生成すると例外が発生すること")
        fun testSquarePositionYOverFlow() {
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquarePosition(0, 8)
            }
            assertAll(
                { assertEquals("SquarePositionのyは0から7の範囲で設定してください", exception.message) },
                { assertEquals("", exception.description) },
            )
        }

        @Test
        @DisplayName("垂直方向の位置がマイナスのSquarePositionのインスタンスを生成すると例外が発生すること")
        fun testSquarePositionYUnderFlow() {
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquarePosition(0, -1)
            }
            assertAll(
                { assertEquals("SquarePositionのyは0から7の範囲で設定してください", exception.message) },
                { assertEquals("", exception.description) },
            )
        }
    }

    @Nested
    @DisplayName("SquareLineHorizontalのテスト")
    inner class SquareLineHorizontalTest {
        @Test
        @DisplayName("SquareLineHorizontalのインスタンスを生成できること")
        fun testSquareLineHorizontal() {
            val squares = (0..7).map { Square(SquarePosition(it, 0), null) }
            val squareLine = SquareLine.SquareLineHorizontal(squares)
            assertAll(
                { assertEquals(8, squareLine.squares.size) },
                { assertEquals(SquareLineType.Horizontal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineHorizontalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineHorizontalSize() {
            val squares = (0..6).map { Square(SquarePosition(it, 0), null) }
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineHorizontal(squares)
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
                Square(SquarePosition(0, 0), null),
                Square(SquarePosition(1, 1), null),
                Square(SquarePosition(2, 0), null),
                Square(SquarePosition(3, 0), null),
                Square(SquarePosition(4, 0), null),
                Square(SquarePosition(5, 0), null),
                Square(SquarePosition(6, 0), null),
                Square(SquarePosition(7, 0), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineHorizontal(squares)
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
            val squares = (0..7).map { Square(SquarePosition(0, it), null) }
            val squareLine = SquareLine.SquareLineVertical(squares)
            assertAll(
                { assertEquals(8, squareLine.squares.size) },
                { assertEquals(SquareLineType.Vertical, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineVerticalのSquare数が不正の場合例外が発生すること")
        fun testSquareLineVerticalSize() {
            val squares = (0..6).map { Square(SquarePosition(0, it), null) }
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineVertical(squares)
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
                Square(SquarePosition(0, 0), null),
                Square(SquarePosition(0, 1), null),
                Square(SquarePosition(1, 2), null),
                Square(SquarePosition(0, 3), null),
                Square(SquarePosition(0, 4), null),
                Square(SquarePosition(0, 5), null),
                Square(SquarePosition(0, 6), null),
                Square(SquarePosition(0, 7), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineVertical(squares)
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
            val squares = (0..7).map { Square(SquarePosition(it, it), null) }
            val squareLine = SquareLine.SquareLineDiagonal(squares)
            assertAll(
                { assertEquals(8, squareLine.squares.size) },
                { assertEquals(SquareLineType.Diagonal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalの配列順序が逆順でもインスタンスを生成できること")
        fun testSquareLineDiagonalReverse() {
            val squares = (0..7).map { Square(SquarePosition(7 - it, 7 - it), null) }
            val squareLine = SquareLine.SquareLineDiagonal(squares)
            assertAll(
                { assertEquals(8, squareLine.squares.size) },
                { assertEquals(SquareLineType.Diagonal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree() {
            val squares = listOf(
                Square(SquarePosition(0, 5), null),
                Square(SquarePosition(1, 6), null),
                Square(SquarePosition(2, 7), null),
            )
            val squareLine = SquareLine.SquareLineDiagonal(squares)
            assertAll(
                { assertEquals(3, squareLine.squares.size) },
                { assertEquals(SquareLineType.Diagonal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalのSquareが3個のインスタンスを生成できること")
        fun testSquareLineDiagonalThree2() {
            val squares = listOf(
                Square(SquarePosition(5, 0), null),
                Square(SquarePosition(6, 1), null),
                Square(SquarePosition(7, 2), null),
            )
            val squareLine = SquareLine.SquareLineDiagonal(squares)
            assertAll(
                { assertEquals(3, squareLine.squares.size) },
                { assertEquals(SquareLineType.Diagonal, squareLine.type) },
            )
        }

        @Test
        @DisplayName("SquareLineDiagonalの盤目の端から端に到達していないインスタンスを生成した場合例外が発生すること")
        fun testSquareLineDiagonalNotReach() {
            val squares = listOf(
                Square(SquarePosition(0, 0), null),
                Square(SquarePosition(1, 1), null),
                Square(SquarePosition(2, 2), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal(squares)
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
                SquareLine.SquareLineDiagonal(listOf())
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
                Square(SquarePosition(0, 0), null),
                Square(SquarePosition(1, 1), null),
                Square(SquarePosition(2, 2), null),
                Square(SquarePosition(3, 3), null),
                Square(SquarePosition(4, 4), null),
                Square(SquarePosition(5, 5), null),
                Square(SquarePosition(6, 6), null),
                Square(SquarePosition(7, 6), null),
            )
            val exception = assertThrows<CustomIllegalArgumentException> {
                SquareLine.SquareLineDiagonal(squares)
            }
            assertAll(
                { assertEquals("SquareLineDiagonalは斜めのSquareを持つ必要があります", exception.message) },
                { assertEquals("", exception.description) },
            )
        }
    }
}