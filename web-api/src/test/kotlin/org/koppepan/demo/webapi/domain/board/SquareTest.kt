package org.koppepan.demo.webapi.domain.board

//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Nested
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.koppepan.demo.webapi.domain.shared.CustomIllegalArgumentException

//class SquareTest {
//    @Nested
//    @DisplayName("Squareのテスト")
//    inner class SquareTest {
//        @Test
//        @DisplayName("Squareのインスタンスを生成できること")
//        fun testSquare() {
//            val square = Square.create(
//                position = SquarePosition.create(0, 0),
//                disk = null,
//            )
//            assertAll(
//                { assertEquals(0, square.position.x) },
//                { assertEquals(0, square.position.y) },
//                { assertNull(square.disk) },
//            )
//        }
//
//        @Test
//        @DisplayName("Squareにdiskを配置できること")
//        fun testPutDisk() {
//            val square = Square.create(
//                position = SquarePosition.create(0, 0),
//                disk = null,
//            )
//            val disk = Disk(DiskType.Light)
//            val newSquare = square.putDisk(disk)
//            assertAll(
//                { assertEquals(0, newSquare.position.x) },
//                { assertEquals(0, newSquare.position.y) },
//                { assertEquals(disk, newSquare.disk) },
//            )
//        }
//
//        @Test
//        @DisplayName("Squareに配置したDiskを白から黒に反転できること")
//        fun testReverseDiskToDark() {
//            val square = Square.create(
//                position = SquarePosition.create(0, 0),
//                disk = Disk(DiskType.Light),
//            )
//            val newSquare = square.reverseDisk()
//            assertAll(
//                { assertEquals(0, newSquare.position.x) },
//                { assertEquals(0, newSquare.position.y) },
//                { assertEquals(DiskType.Dark, newSquare.disk?.diskType) },
//            )
//        }
//
//        @Test
//        @DisplayName("Squareに配置したDiskを黒から白に反転できること")
//        fun testReverseDiskToLight() {
//            val square = Square.create(
//                position = SquarePosition.create(0, 0),
//                disk = Disk(DiskType.Dark),
//            )
//            val newSquare = square.reverseDisk()
//            assertAll(
//                { assertEquals(0, newSquare.position.x) },
//                { assertEquals(0, newSquare.position.y) },
//                { assertEquals(DiskType.Light, newSquare.disk?.diskType) },
//            )
//        }
//    }
//
//    @Nested
//    @DisplayName("SquarePositionのテスト")
//    inner class SquarePositionTest {
//        @Test
//        @DisplayName("SquarePositionのインスタンスを生成できること")
//        fun testSquarePosition() {
//            val squarePosition = SquarePosition.create(0, 0)
//            assertAll(
//                { assertEquals(0, squarePosition.x) },
//                { assertEquals(0, squarePosition.y) },
//            )
//        }
//
//        @Test
//        @DisplayName("SquarePositionの位置が同じならばequalとなること")
//        fun testSquarePositionEqual() {
//            val squarePosition1 = SquarePosition.create(0, 0)
//            val squarePosition2 = SquarePosition.create(0, 0)
//            assertEquals(squarePosition1, squarePosition2)
//        }
//
//        @Test
//        @DisplayName("水平方向の位置が上限を超えたSquarePositionのインスタンスを生成すると例外が発生すること")
//        fun testSquarePositionXOverFlow() {
//            val exception = assertThrows<CustomIllegalArgumentException> {
//                SquarePosition.create(8, 0)
//            }
//            assertAll(
//                { assertEquals("SquarePositionのxは0から7の範囲で設定してください", exception.message) },
//                { assertEquals("", exception.description) },
//            )
//        }
//
//        @Test
//        @DisplayName("水平方向の位置がマイナスのSquarePositionのインスタンスを生成すると例外が発生すること")
//        fun testSquarePositionXUnderFlow() {
//            val exception = assertThrows<CustomIllegalArgumentException> {
//                SquarePosition.create(-1, 0)
//            }
//            assertAll(
//                { assertEquals("SquarePositionのxは0から7の範囲で設定してください", exception.message) },
//                { assertEquals("", exception.description) },
//            )
//        }
//
//        @Test
//        @DisplayName("垂直方向の位置が上限を超えたSquarePositionのインスタンスを生成すると例外が発生すること")
//        fun testSquarePositionYOverFlow() {
//            val exception = assertThrows<CustomIllegalArgumentException> {
//                SquarePosition.create(0, 8)
//            }
//            assertAll(
//                { assertEquals("SquarePositionのyは0から7の範囲で設定してください", exception.message) },
//                { assertEquals("", exception.description) },
//            )
//        }
//
//        @Test
//        @DisplayName("垂直方向の位置がマイナスのSquarePositionのインスタンスを生成すると例外が発生すること")
//        fun testSquarePositionYUnderFlow() {
//            val exception = assertThrows<CustomIllegalArgumentException> {
//                SquarePosition.create(0, -1)
//            }
//            assertAll(
//                { assertEquals("SquarePositionのyは0から7の範囲で設定してください", exception.message) },
//                { assertEquals("", exception.description) },
//            )
//        }
//    }
//}