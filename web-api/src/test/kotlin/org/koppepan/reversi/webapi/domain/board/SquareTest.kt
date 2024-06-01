package org.koppepan.reversi.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SquareTest {
    @Test
    @DisplayName("Squareのインスタンスを生成できること")
    fun testSquare() {
        val square = Square.create(
            position = SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE),
            disk = null,
        )
        assertAll(
            { assertEquals(HorizontalPosition.ONE, square.position.x) },
            { assertEquals(VerticalPosition.ONE, square.position.y) },
            { assertNull(square.disk) },
        )
    }

    @Test
    @DisplayName("Squareにdiskを配置できること")
    fun testPutDisk() {
        val square = Square.create(
            position = SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE),
            disk = null,
        )
        val disk = Disk(DiskType.Light)
        val newSquare = square.putDisk(disk)
        assertAll(
            { assertEquals(HorizontalPosition.ONE, newSquare.position.x) },
            { assertEquals(VerticalPosition.ONE, newSquare.position.y) },
            { assertEquals(disk, newSquare.disk) },
        )
    }

    @Test
    @DisplayName("Squareに配置したDiskを白から黒に反転できること")
    fun testReverseDiskToDark() {
        val square = Square.create(
            position = SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE),
            disk = Disk(DiskType.Light),
        )
        val newSquare = square.reverseDisk()
        assertAll(
            { assertEquals(HorizontalPosition.ONE, newSquare.position.x) },
            { assertEquals(VerticalPosition.ONE, newSquare.position.y) },
            { assertEquals(DiskType.Dark, newSquare.disk?.diskType) },
        )
    }

    @Test
    @DisplayName("Squareに配置したDiskを黒から白に反転できること")
    fun testReverseDiskToLight() {
        val square = Square.create(
            position = SquarePosition(HorizontalPosition.ONE, VerticalPosition.ONE),
            disk = Disk(DiskType.Dark),
        )
        val newSquare = square.reverseDisk()
        assertAll(
            { assertEquals(HorizontalPosition.ONE, newSquare.position.x) },
            { assertEquals(VerticalPosition.ONE, newSquare.position.y) },
            { assertEquals(DiskType.Light, newSquare.disk?.diskType) },
        )
    }
}