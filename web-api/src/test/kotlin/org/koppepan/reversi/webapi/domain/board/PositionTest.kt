package org.koppepan.reversi.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    @DisplayName("Positionを作成する事ができること")
    fun testCreate() {
        val position = Position(HorizontalPosition.D, VerticalPosition.FOUR)
        assertEquals(HorizontalPosition.D, position.x)
        assertEquals(VerticalPosition.FOUR, position.y)
    }

    @Test
    @DisplayName("Position同士が等しいかどうかを判定する事ができること")
    fun testEquals() {
        val position1 = Position(HorizontalPosition.D, VerticalPosition.FOUR)
        val position2 = Position(HorizontalPosition.D, VerticalPosition.FOUR)
        val position3 = Position(HorizontalPosition.E, VerticalPosition.FIVE)
        assertEquals(position1, position2)
        assertNotEquals(position1, position3)
    }

    @Test
    @DisplayName("PositionのX座標を正しく比較できること")
    fun testCompareX() {
        val position1 = Position(HorizontalPosition.D, VerticalPosition.FOUR)
        val position2 = Position(HorizontalPosition.E, VerticalPosition.FOUR)
        val position3 = Position(HorizontalPosition.F, VerticalPosition.FOUR)
        val position4 = Position(HorizontalPosition.D, VerticalPosition.FOUR)
        assertTrue(position1.x < position2.x)
        assertTrue(position3.x > position1.x)
        assertTrue(position1.x == position4.x)
    }

    @Test
    @DisplayName("PositionのY座標を正しく比較できること")
    fun testCompareY() {
        val position1 = Position(HorizontalPosition.D, VerticalPosition.FOUR)
        val position2 = Position(HorizontalPosition.D, VerticalPosition.FIVE)
        val position3 = Position(HorizontalPosition.D, VerticalPosition.SIX)
        val position4 = Position(HorizontalPosition.D, VerticalPosition.FOUR)
        assertTrue(position1.y < position2.y)
        assertTrue(position3.y > position1.y)
        assertTrue(position1.y == position4.y)
    }

    @Nested
    @DisplayName("隣接位置の取得")
    inner class TestGetAdjacentPositions {
        @Test
        @DisplayName("Positionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositions() {
            val position = Position(HorizontalPosition.D, VerticalPosition.FOUR)
            val adjacentPositions = position.getAdjacentPositions()
            assertEquals(8, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.C, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.D, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.E, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.C, VerticalPosition.FOUR)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.E, VerticalPosition.FOUR)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.C, VerticalPosition.FIVE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.D, VerticalPosition.FIVE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.E, VerticalPosition.FIVE)))
        }

        @Test
        @DisplayName("盤の左隅を表すPositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtLeftMiddle() {
            val position = Position(HorizontalPosition.A, VerticalPosition.FOUR)
            val adjacentPositions = position.getAdjacentPositions()
            assertEquals(5, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.A, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.A, VerticalPosition.FIVE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.B, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.B, VerticalPosition.FOUR)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.B, VerticalPosition.FIVE)))
        }

        @Test
        @DisplayName("盤の右隅を表すPositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtRightMiddle() {
            val position = Position(HorizontalPosition.H, VerticalPosition.FOUR)
            val adjacentPositions = position.getAdjacentPositions()
            assertEquals(5, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.H, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.H, VerticalPosition.FIVE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.G, VerticalPosition.THREE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.G, VerticalPosition.FOUR)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.G, VerticalPosition.FIVE)))
        }

        @Test
        @DisplayName("盤の上隅を表すPositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtTopMiddle() {
            val position = Position(HorizontalPosition.D, VerticalPosition.ONE)
            val adjacentPositions = position.getAdjacentPositions()
            assertEquals(5, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.C, VerticalPosition.ONE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.E, VerticalPosition.ONE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.C, VerticalPosition.TWO)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.D, VerticalPosition.TWO)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.E, VerticalPosition.TWO)))
        }

        @Test
        @DisplayName("盤の下隅を表すPositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtBottomMiddle() {
            val position = Position(HorizontalPosition.D, VerticalPosition.EIGHT)
            val adjacentPositions = position.getAdjacentPositions()
            assertEquals(5, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.C, VerticalPosition.EIGHT)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.E, VerticalPosition.EIGHT)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.C, VerticalPosition.SEVEN)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.D, VerticalPosition.SEVEN)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.E, VerticalPosition.SEVEN)))
        }

        @Test
        @DisplayName("盤の左上角を表すPositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtTopLeft() {
            val position = Position(HorizontalPosition.A, VerticalPosition.ONE)
            val adjacentPositions = position.getAdjacentPositions()
            assertEquals(3, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.A, VerticalPosition.TWO)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.B, VerticalPosition.ONE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.B, VerticalPosition.TWO)))
        }

        @Test
        @DisplayName("盤の右上角を表すPositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtTopRight() {
            val position = Position(HorizontalPosition.H, VerticalPosition.ONE)
            val adjacentPositions = position.getAdjacentPositions()
            assertEquals(3, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.H, VerticalPosition.TWO)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.G, VerticalPosition.ONE)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.G, VerticalPosition.TWO)))
        }

        @Test
        @DisplayName("盤の左下角を表すPositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtBottomLeft() {
            val position = Position(HorizontalPosition.A, VerticalPosition.EIGHT)
            val adjacentPositions = position.getAdjacentPositions()
            assertEquals(3, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.A, VerticalPosition.SEVEN)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.B, VerticalPosition.EIGHT)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.B, VerticalPosition.SEVEN)))
        }

        @Test
        @DisplayName("盤の右下角を表すPositionの隣接する位置を取得する事ができること")
        fun testGetAdjacentPositionsAtBottomRight() {
            val position = Position(HorizontalPosition.H, VerticalPosition.EIGHT)
            val adjacentPositions = position.getAdjacentPositions()
            assertEquals(3, adjacentPositions.size)
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.H, VerticalPosition.SEVEN)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.G, VerticalPosition.EIGHT)))
            assertTrue(adjacentPositions.contains(Position(HorizontalPosition.G, VerticalPosition.SEVEN)))
        }
    }
}