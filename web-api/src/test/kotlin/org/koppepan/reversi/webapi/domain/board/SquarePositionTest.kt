package org.koppepan.reversi.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SquarePositionTest {
    @Test
    @DisplayName("SquarePositionを作成する事ができること")
    fun testCreate() {
        val squarePosition = SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR)
        assertEquals(HorizontalPosition.D, squarePosition.x)
        assertEquals(VerticalPosition.FOUR, squarePosition.y)
    }

    @Test
    @DisplayName("SquarePosition同士が等しいかどうかを判定する事ができること")
    fun testEquals() {
        val squarePosition1 = SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR)
        val squarePosition2 = SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR)
        val squarePosition3 = SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE)
        assertEquals(squarePosition1, squarePosition2)
        assertNotEquals(squarePosition1, squarePosition3)
    }

    @Test
    @DisplayName("SquarePositionのX座標を正しく比較できること")
    fun testCompareX() {
        val squarePosition1 = SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR)
        val squarePosition2 = SquarePosition(HorizontalPosition.E, VerticalPosition.FOUR)
        val squarePosition3 = SquarePosition(HorizontalPosition.F, VerticalPosition.FOUR)
        val squarePosition4 = SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR)
        assertTrue(squarePosition1.x < squarePosition2.x)
        assertTrue(squarePosition3.x > squarePosition1.x)
        assertTrue(squarePosition1.x == squarePosition4.x)
    }

    @Test
    @DisplayName("SquarePositionのY座標を正しく比較できること")
    fun testCompareY() {
        val squarePosition1 = SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR)
        val squarePosition2 = SquarePosition(HorizontalPosition.D, VerticalPosition.FIVE)
        val squarePosition3 = SquarePosition(HorizontalPosition.D, VerticalPosition.SIX)
        val squarePosition4 = SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR)
        assertTrue(squarePosition1.y < squarePosition2.y)
        assertTrue(squarePosition3.y > squarePosition1.y)
        assertTrue(squarePosition1.y == squarePosition4.y)
    }

    @Nested
    @DisplayName("隣接位置の取得")
    inner class TestGetAdjacentPositions {
        @Test
        @DisplayName("SquarePositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositions() {
            val squarePosition = SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR)
            val adjacentPositions = squarePosition.getAdjacentPositions()
            assertEquals(8, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.C, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.D, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.E, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.C, VerticalPosition.FOUR)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.E, VerticalPosition.FOUR)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.C, VerticalPosition.FIVE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.D, VerticalPosition.FIVE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE)))
        }

        @Test
        @DisplayName("盤の左隅を表すSquarePositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtLeftMiddle() {
            val squarePosition = SquarePosition(HorizontalPosition.A, VerticalPosition.FOUR)
            val adjacentPositions = squarePosition.getAdjacentPositions()
            assertEquals(5, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.A, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.A, VerticalPosition.FIVE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.B, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.B, VerticalPosition.FOUR)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.B, VerticalPosition.FIVE)))
        }

        @Test
        @DisplayName("盤の右隅を表すSquarePositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtRightMiddle() {
            val squarePosition = SquarePosition(HorizontalPosition.H, VerticalPosition.FOUR)
            val adjacentPositions = squarePosition.getAdjacentPositions()
            assertEquals(5, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.H, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.H, VerticalPosition.FIVE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.G, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.G, VerticalPosition.FOUR)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.G, VerticalPosition.FIVE)))
        }

        @Test
        @DisplayName("盤の上隅を表すSquarePositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtTopMiddle() {
            val squarePosition = SquarePosition(HorizontalPosition.D, VerticalPosition.ONE)
            val adjacentPositions = squarePosition.getAdjacentPositions()
            assertEquals(5, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.C, VerticalPosition.ONE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.E, VerticalPosition.ONE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.C, VerticalPosition.TWO)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.D, VerticalPosition.TWO)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.E, VerticalPosition.TWO)))
        }

        @Test
        @DisplayName("盤の下隅を表すSquarePositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtBottomMiddle() {
            val squarePosition = SquarePosition(HorizontalPosition.D, VerticalPosition.EIGHT)
            val adjacentPositions = squarePosition.getAdjacentPositions()
            assertEquals(5, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.C, VerticalPosition.EIGHT)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.E, VerticalPosition.EIGHT)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.C, VerticalPosition.SEVEN)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.D, VerticalPosition.SEVEN)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.E, VerticalPosition.SEVEN)))
        }

        @Test
        @DisplayName("盤の左上角を表すSquarePositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtTopLeft() {
            val squarePosition = SquarePosition(HorizontalPosition.A, VerticalPosition.ONE)
            val adjacentPositions = squarePosition.getAdjacentPositions()
            assertEquals(3, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.A, VerticalPosition.TWO)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.B, VerticalPosition.ONE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.B, VerticalPosition.TWO)))
        }

        @Test
        @DisplayName("盤の右上角を表すSquarePositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtTopRight() {
            val squarePosition = SquarePosition(HorizontalPosition.H, VerticalPosition.ONE)
            val adjacentPositions = squarePosition.getAdjacentPositions()
            assertEquals(3, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.H, VerticalPosition.TWO)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.G, VerticalPosition.ONE)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.G, VerticalPosition.TWO)))
        }

        @Test
        @DisplayName("盤の左下角を表すSquarePositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtBottomLeft() {
            val squarePosition = SquarePosition(HorizontalPosition.A, VerticalPosition.EIGHT)
            val adjacentPositions = squarePosition.getAdjacentPositions()
            assertEquals(3, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.A, VerticalPosition.SEVEN)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.B, VerticalPosition.EIGHT)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.B, VerticalPosition.SEVEN)))
        }

        @Test
        @DisplayName("盤の右下角を表すSquarePositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtBottomRight() {
            val squarePosition = SquarePosition(HorizontalPosition.H, VerticalPosition.EIGHT)
            val adjacentPositions = squarePosition.getAdjacentPositions()
            assertEquals(3, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.H, VerticalPosition.SEVEN)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.G, VerticalPosition.EIGHT)))
            assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.G, VerticalPosition.SEVEN)))
        }
    }
}