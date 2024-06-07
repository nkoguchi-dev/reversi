package org.koppepan.reversi.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koppepan.reversi.webapi.domain.player.Player
import org.koppepan.reversi.webapi.domain.player.PlayerMove
import org.koppepan.reversi.webapi.domain.player.PlayerName
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

    @Nested
    @DisplayName("ディスクを裏返すテスト")
    inner class TestReverseDisk {
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

        @Test
        @DisplayName("水平方向に挟んだ相手のディスクを裏返す事ができる")
        fun testPutDiskToHorizontalPosition() {
            val board = Board.create()
            val newBoard = board.putDisk(
                PlayerMove(
                    PlayerNumber.PLAYER1,
                    SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE),
                    Disk(DiskType.Dark)
                )
            )
            assertAll(
                {
                    assertEquals(
                        Disk(DiskType.Dark),
                        newBoard.getDisk(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE))
                    )
                },
                {
                    assertEquals(
                        Disk(DiskType.Dark),
                        newBoard.getDisk(SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE))
                    )
                },
            )
        }

        @Test
        @DisplayName("斜め方向に挟んだ相手のディスクを裏返す事ができる")
        fun testPutDiskToDiagonalPosition() {
            val board = Board.create()
            val newBoard = board.putDisk(
                PlayerMove(
                    PlayerNumber.PLAYER1,
                    SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE),
                    Disk(DiskType.Dark)
                )
            ).putDisk(
                PlayerMove(
                    PlayerNumber.PLAYER2,
                    SquarePosition(HorizontalPosition.F, VerticalPosition.SIX),
                    Disk(DiskType.Light)
                )
            )
            assertAll(
                {
                    assertEquals(
                        Disk(DiskType.Light),
                        newBoard.getDisk(SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE))
                    )
                },
                {
                    assertEquals(
                        Disk(DiskType.Light),
                        newBoard.getDisk(SquarePosition(HorizontalPosition.F, VerticalPosition.SIX))
                    )
                },
            )
        }
    }

    @Nested
    @DisplayName("配置可能な全てのSquareを取得するテスト")
    inner class TestGetAllPuttableSquares {
        @Test
        @DisplayName("初期状態のDiskに配置可能な全てのSquareを取得する事ができる")
        fun testGetAllPuttableSquares() {
            val board = Board.create()
            val actual = board.getAllPuttableSquares(PlayerNumber.PLAYER1)
            val expected = listOf(
                SquarePosition(HorizontalPosition.C, VerticalPosition.FOUR),
                SquarePosition(HorizontalPosition.D, VerticalPosition.THREE),
                SquarePosition(HorizontalPosition.E, VerticalPosition.SIX),
                SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE),
            )
            assertEquals(expected, actual)
        }

        @Test
        @DisplayName("１手進めた盤目に配置可能な全てのSquareを取得する事ができる")
        fun testGetAllPuttableSquaresAfterOneMove() {
            val board = Board.create()
            val newBoard = board.putDisk(
                PlayerMove(
                    PlayerNumber.PLAYER1,
                    SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE),
                    Disk(DiskType.Dark)
                )
            )
            val actual = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER2)
            val expected = listOf(
                SquarePosition(HorizontalPosition.D, VerticalPosition.SIX),
                SquarePosition(HorizontalPosition.F, VerticalPosition.FOUR),
                SquarePosition(HorizontalPosition.F, VerticalPosition.SIX),
            )
            assertEquals(expected, actual)
        }

        @Test
        @DisplayName("配置可能なSquareがない場合は空リストを返す（黒が勝つパターン）")
        fun testGetAllPuttableSquaresWhenNoPuttableSquare() {
            val board = Board.create()
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val player2 = Player.createPlayer2(PlayerName("Player2"))
            val newBoard = board
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(SquarePosition(HorizontalPosition.D, VerticalPosition.SIX)))
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.C, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(SquarePosition(HorizontalPosition.F, VerticalPosition.FOUR)))
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.E, VerticalPosition.SEVEN)))
                .putDisk(player2.createMove(SquarePosition(HorizontalPosition.F, VerticalPosition.SIX)))
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.G, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(SquarePosition(HorizontalPosition.E, VerticalPosition.SIX)))
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.E, VerticalPosition.THREE)))
            val actualPlayer1 = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER1)
            val actualPlayer2 = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER2)
            assertAll(
                { assertEquals(emptyList<SquarePosition>(), actualPlayer1) },
                { assertEquals(emptyList<SquarePosition>(), actualPlayer2) },
            )
        }

        @Test
        @DisplayName("配置可能なSquareがない場合は空リストを返す（白が勝つパターン）")
        fun testGetAllPuttableSquaresWhenNoPuttableSquare2() {
            val board = Board.create()
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val player2 = Player.createPlayer2(PlayerName("Player2"))
            val newBoard = board
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(SquarePosition(HorizontalPosition.F, VerticalPosition.SIX)))
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.E, VerticalPosition.SIX)))
                .putDisk(player2.createMove(SquarePosition(HorizontalPosition.F, VerticalPosition.FOUR)))
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.E, VerticalPosition.THREE)))
                .putDisk(player2.createMove(SquarePosition(HorizontalPosition.D, VerticalPosition.TWO)))
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.D, VerticalPosition.THREE)))
                .putDisk(player2.createMove(SquarePosition(HorizontalPosition.D, VerticalPosition.SIX)))
                .putDisk(player1.createMove(SquarePosition(HorizontalPosition.C, VerticalPosition.FOUR)))
                .putDisk(player2.createMove(SquarePosition(HorizontalPosition.B, VerticalPosition.FOUR)))
            val actualPlayer1 = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER1)
            val actualPlayer2 = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER2)
            assertAll(
                { assertEquals(emptyList<SquarePosition>(), actualPlayer1) },
                { assertEquals(emptyList<SquarePosition>(), actualPlayer2) },
            )
        }
    }
}