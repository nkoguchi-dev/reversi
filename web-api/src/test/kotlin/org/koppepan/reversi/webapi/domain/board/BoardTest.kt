package org.koppepan.reversi.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koppepan.reversi.webapi.domain.player.PlayerNumber
import org.koppepan.reversi.webapi.domain.shared.CustomIllegalArgumentException

class BoardTest {
    @Test
    @DisplayName("初期状態のBoardを作成する事ができる")
    fun testCreate() {
        val board = Board.create()
        val diskMap = board.diskMap
        assertEquals(64, diskMap.value.size)
        assertEquals(
            Disk(DiskType.Light),
            board.getDisk(SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR))
        )
        assertEquals(
            Disk(DiskType.Light),
            board.getDisk(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE))
        )
        assertEquals(
            Disk(DiskType.Dark),
            board.getDisk(SquarePosition(HorizontalPosition.D, VerticalPosition.FIVE))
        )
        assertEquals(
            Disk(DiskType.Dark),
            board.getDisk(SquarePosition(HorizontalPosition.E, VerticalPosition.FOUR))
        )
    }

    @Nested
    @DisplayName("ディスク配置のテスト")
    inner class TestPutDisk {
        @Test
        @DisplayName("ディスクを置く事ができる")
        fun testPutDisk() {
            val board = Board.create()
            val putPosition = SquarePosition(HorizontalPosition.E, VerticalPosition.THREE)
            val newBoard = board.putDisk(
                PlayerMove(PlayerNumber.PLAYER1, putPosition, Disk(DiskType.Light))
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
                    PlayerMove(
                        PlayerNumber.PLAYER1,
                        SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR),
                        Disk(DiskType.Light)
                    )
                )
            }
            assertEquals("ディスクを置く事はできません", exception.message)
            assertEquals(
                "既にディスクが置かれている位置にディスクを置くことはできません。position: (D, FOUR)",
                exception.description
            )
        }

        @Test
        @DisplayName("相手のディスクを裏返せる位置にディスクを置く事ができる")
        fun testPutDiskToPositionWithOpponentDisk() {
            val board = Board.create()
            val newBoard = board.putDisk(
                PlayerMove(
                    PlayerNumber.PLAYER1,
                    SquarePosition(HorizontalPosition.E, VerticalPosition.SIX),
                    Disk(DiskType.Dark),
                )
            )
            val actual = newBoard.getDisk(SquarePosition(HorizontalPosition.E, VerticalPosition.SIX))
            assertEquals(Disk(DiskType.Dark), actual)
        }

        @Test
        @DisplayName("相手のディスクを裏返せない位置にディスクを置く事ができない")
        fun testPutDiskToPositionWithoutOpponentDisk() {
            val board = Board.create()
            val exception = assertThrows(CustomIllegalArgumentException::class.java) {
                board.putDisk(
                    PlayerMove(
                        PlayerNumber.PLAYER1,
                        SquarePosition(HorizontalPosition.E, VerticalPosition.SIX),
                        Disk(DiskType.Light),
                    )
                )
            }
            assertEquals("ディスクを置く事はできません", exception.message)
            assertEquals(
                "相手のディスクを裏返す事ができない位置にディスクを置くことはできません。PlayerMove(number=PLAYER1, position=(E, SIX), disk=Disk(diskType=Light))",
                exception.description
            )
        }
    }

    @Test
    @DisplayName("垂直方向に挟んだ相手のディスクを裏返す事ができる")
    fun testPutDiskToVerticalPosition() {
        val board = Board.create()
        val newBoard = board.putDisk(
            PlayerMove(
                PlayerNumber.PLAYER1,
                SquarePosition(HorizontalPosition.D, VerticalPosition.THREE),
                Disk(DiskType.Dark)
            )
        )
        assertAll(
            {
                assertEquals(
                    Disk(DiskType.Dark),
                    newBoard.getDisk(SquarePosition(HorizontalPosition.D, VerticalPosition.THREE))
                )
            },
            {
                assertEquals(
                    Disk(DiskType.Dark),
                    newBoard.getDisk(SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR))
                )
            },
        )
    }
}