package org.koppepan.reversi.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SquarePositionTest {
    @Test
    @DisplayName("SquarePositionを作成する事ができること")
    fun testCreate() {
        val squarePosition = SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR)
        assertEquals(HorizontalPosition.FOUR, squarePosition.x)
        assertEquals(VerticalPosition.FOUR, squarePosition.y)
    }

    @Test
    @DisplayName("SquarePosition同士が等しいかどうかを判定する事ができること")
    fun testEquals() {
        val squarePosition1 = SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR)
        val squarePosition2 = SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR)
        val squarePosition3 = SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FIVE)
        assertEquals(squarePosition1, squarePosition2)
        assertNotEquals(squarePosition1, squarePosition3)
    }

    @Test
    @DisplayName("SquarePositionの隣接する位置を取得する事ができること")
    fun testGetAdjacentPositions() {
        val squarePosition = SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR)
        val adjacentPositions = squarePosition.getAdjacentPositions()
        assertEquals(8, adjacentPositions.size)
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.THREE, VerticalPosition.THREE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.THREE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.THREE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.THREE, VerticalPosition.FOUR)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FOUR)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.THREE, VerticalPosition.FIVE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FIVE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FIVE)))
    }

    @Test
    @DisplayName("盤の左隅を表すSquarePositionの隣接する位置を取得する事ができること")
    fun testGetAdjacentPositionsAtLeftMiddle() {
        val squarePosition = SquarePosition(HorizontalPosition.ONE, VerticalPosition.FOUR)
        val adjacentPositions = squarePosition.getAdjacentPositions()
        assertEquals(5, adjacentPositions.size)
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.ONE, VerticalPosition.THREE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.ONE, VerticalPosition.FIVE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.TWO, VerticalPosition.THREE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.TWO, VerticalPosition.FOUR)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.TWO, VerticalPosition.FIVE)))
    }

    @Test
    @DisplayName("盤の右隅を表すSquarePositionの隣接する位置を取得する事ができること")
    fun testGetAdjacentPositionsAtRightMiddle() {
        val squarePosition = SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.FOUR)
        val adjacentPositions = squarePosition.getAdjacentPositions()
        assertEquals(5, adjacentPositions.size)
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.THREE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.FIVE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.THREE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.FOUR)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.FIVE)))
    }

    @Test
    @DisplayName("盤の上隅を表すSquarePositionの隣接する位置を取得する事ができること")
    fun testGetAdjacentPositionsAtTopMiddle() {
        val squarePosition = SquarePosition(HorizontalPosition.FOUR, VerticalPosition.ONE)
        val adjacentPositions = squarePosition.getAdjacentPositions()
        assertEquals(5, adjacentPositions.size)
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.THREE, VerticalPosition.ONE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.ONE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.THREE, VerticalPosition.TWO)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.TWO)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.TWO)))
    }

    @Test
    @DisplayName("盤の下隅を表すSquarePositionの隣接する位置を取得する事ができること")
    fun testGetAdjacentPositionsAtBottomMiddle() {
        val squarePosition = SquarePosition(HorizontalPosition.FOUR, VerticalPosition.EIGHT)
        val adjacentPositions = squarePosition.getAdjacentPositions()
        assertEquals(5, adjacentPositions.size)
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.THREE, VerticalPosition.EIGHT)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.EIGHT)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.THREE, VerticalPosition.SEVEN)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.SEVEN)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.SEVEN)))
    }

    @Test
    @DisplayName("盤の左上角を表すSquarePositionの隣接する位置を取得する事ができること")
    fun testGetAdjacentPositionsAtTopLeft() {
        val squarePosition = SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE)
        val adjacentPositions = squarePosition.getAdjacentPositions()
        assertEquals(3, adjacentPositions.size)
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.ONE, VerticalPosition.TWO)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.TWO, VerticalPosition.ONE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.TWO, VerticalPosition.TWO)))
    }

    @Test
    @DisplayName("盤の右上角を表すSquarePositionの隣接する位置を取得する事ができること")
    fun testGetAdjacentPositionsAtTopRight() {
        val squarePosition = SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.ONE)
        val adjacentPositions = squarePosition.getAdjacentPositions()
        assertEquals(3, adjacentPositions.size)
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.TWO)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.ONE)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.TWO)))
    }

    @Test
    @DisplayName("盤の左下角を表すSquarePositionの隣接する位置を取得する事ができること")
    fun testGetAdjacentPositionsAtBottomLeft() {
        val squarePosition = SquarePosition(HorizontalPosition.ONE, VerticalPosition.EIGHT)
        val adjacentPositions = squarePosition.getAdjacentPositions()
        assertEquals(3, adjacentPositions.size)
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.ONE, VerticalPosition.SEVEN)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.TWO, VerticalPosition.EIGHT)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.TWO, VerticalPosition.SEVEN)))
    }

    @Test
    @DisplayName("盤の右下角を表すSquarePositionの隣接する位置を取得する事ができること")
    fun testGetAdjacentPositionsAtBottomRight() {
        val squarePosition = SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.EIGHT)
        val adjacentPositions = squarePosition.getAdjacentPositions()
        assertEquals(3, adjacentPositions.size)
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.EIGHT, VerticalPosition.SEVEN)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.EIGHT)))
        assertTrue(adjacentPositions.contains(SquarePosition(HorizontalPosition.SEVEN, VerticalPosition.SEVEN)))
    }
}