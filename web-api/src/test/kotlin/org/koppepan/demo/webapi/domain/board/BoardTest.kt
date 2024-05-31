package org.koppepan.demo.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koppepan.demo.webapi.domain.shared.CustomIllegalArgumentException

class BoardTest {
    @Test
    @DisplayName("初期状態のBoardを作成する事ができる")
    fun testCreate() {
        val board = Board.create()
        val diskMap = board.diskMap
        assertEquals(64, diskMap.size)
        assertEquals(
            Disk(DiskType.Light),
            board.getDisk(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR))
        )
        assertEquals(
            Disk(DiskType.Light),
            board.getDisk(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FIVE))
        )
        assertEquals(
            Disk(DiskType.Dark),
            board.getDisk(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FIVE))
        )
        assertEquals(
            Disk(DiskType.Dark),
            board.getDisk(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.FOUR))
        )
    }

    @Nested
    @DisplayName("ディスク配置のテスト")
    inner class TestPutDisk {
        @Test
        @DisplayName("ディスクを置く事ができる")
        fun testPutDisk() {
            val board = Board.create()
            val putPosition = SquarePosition(HorizontalPosition.FIVE, VerticalPosition.THREE)
            val newBoard = board.putDisk(
                PlayerMove.create(putPosition, Disk(DiskType.Light))
            )
            assertEquals(
                Disk(DiskType.Light),
                newBoard.getDisk(putPosition),
            )
        }

        @Test
        @DisplayName("既にディスクが置かれている場所にディスクを置く事はできない")
        fun testPutDiskToAlreadyOccupiedPosition() {
            val board = Board.create()
            val exception = assertThrows(CustomIllegalArgumentException::class.java) {
                board.putDisk(
                    PlayerMove.create(
                        SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR),
                        Disk(DiskType.Light)
                    )
                )
            }
            assertEquals("ディスクを置く事はできません", exception.message)
            assertEquals("既に(FOUR, FOUR)にディスクが置かれています", exception.description)
        }

//        @Test
//        @DisplayName("隣り合う場所に相手のディスクがある場合はディスクを置く事ができる")
//        fun testPutDiskToPositionWithOpponentDisk() {
//            val board = Board.create()
//            val newBoard = board.putDisk(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.SIX), Disk(DiskType.Light))
//            assertEquals(Disk(DiskType.Light), newBoard.getDisk(SquarePosition(HorizontalPosition.FIVE, VerticalPosition.SIX)))
//        }
//
//        @Test
//        @DisplayName("隣り合う場所に相手のディスクがない場合ディスクを置く事はできない")
//        fun testPutDiskToPositionWithoutOpponentDisk() {
//            val board = Board.create()
//            val exception = assertThrows(CustomIllegalArgumentException::class.java) {
//                board.putDisk(SquarePosition(HorizontalPosition.THREE, VerticalPosition.THREE), Disk(DiskType.Light))
//            }
//            assertEquals("ディスクを置く事はできません", exception.message)
//            assertEquals("隣り合う場所に相手のディスクがありません。position: (THREE, THREE)", exception.description)
//        }
    }

//    @Test
//    @DisplayName("垂直方向に挟んだ相手のディスクを裏返す事ができる")
//    fun testReverseDisk() {
//        val diskMap: MutableMap<SquarePosition, Disk?> = mutableMapOf()
//        diskMap[SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR)] = Disk(DiskType.Light)
//        diskMap[SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FIVE)] = Disk(DiskType.Light)
//        diskMap[SquarePosition(HorizontalPosition.FOUR, VerticalPosition.SIX)] = Disk(DiskType.Dark)
//
//        val board = Board.mappedInitialize(diskMap)
//        val newBoard = board.putDisk(
//            PlayerMove.create(
//                SquarePosition(HorizontalPosition.FOUR, VerticalPosition.THREE),
//                Disk(DiskType.Dark)
//            )
//        )
//        assertAll(
//            {
//                assertEquals(
//                    Disk(DiskType.Dark),
//                    newBoard.getDisk(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.THREE))
//                )
//            },
//            {
//                assertEquals(
//                    Disk(DiskType.Dark),
//                    newBoard.getDisk(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FOUR))
//                )
//            },
//            {
//                assertEquals(
//                    Disk(DiskType.Dark),
//                    newBoard.getDisk(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.FIVE))
//                )
//            },
//            {
//                assertEquals(
//                    Disk(DiskType.Dark),
//                    newBoard.getDisk(SquarePosition(HorizontalPosition.FOUR, VerticalPosition.SIX))
//                )
//            },
//        )
//    }
}