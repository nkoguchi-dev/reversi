package org.koppepan.reversi.webapi.domain.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.Player
import org.koppepan.reversi.webapi.domain.player.PlayerName
import org.koppepan.reversi.webapi.domain.player.PlayerNumber
import org.koppepan.reversi.webapi.domain.shared.CustomIllegalArgumentException
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class BoardTest {
    @Mock
    private lateinit var idGenerator: IdGenerator

    @Test
    @DisplayName("初期状態のBoardを作成する事ができる")
    fun testCreate() {
        val board = Board.create()
        val diskMap = board.diskMap
        assertEquals(64, diskMap.value.size)
        assertEquals(
            Disk(DiskType.Light),
            board.getDisk(Position(HorizontalPosition.D, VerticalPosition.FOUR))
        )
        assertEquals(
            Disk(DiskType.Light),
            board.getDisk(Position(HorizontalPosition.E, VerticalPosition.FIVE))
        )
        assertEquals(
            Disk(DiskType.Dark),
            board.getDisk(Position(HorizontalPosition.D, VerticalPosition.FIVE))
        )
        assertEquals(
            Disk(DiskType.Dark),
            board.getDisk(Position(HorizontalPosition.E, VerticalPosition.FOUR))
        )
    }

    @Nested
    @DisplayName("ディスク配置のテスト")
    inner class TestPutDisk {
        @Test
        @DisplayName("ディスクを置く事ができる")
        fun testPutDisk() {
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val board = Board.create()
            val putPosition = Position(HorizontalPosition.F, VerticalPosition.FIVE)
            val newBoard = board.putDisk(player1.createMove(idGenerator, putPosition))
            assertEquals(
                Disk(DiskType.Dark),
                newBoard.getDisk(putPosition),
            )
        }

        @Test
        @DisplayName("既にディスクが置かれている場所にディスクを置く事はできない")
        fun testPutDiskToAlreadyOccupiedPosition() {
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val board = Board.create()
            val exception = assertThrows(CustomIllegalArgumentException::class.java) {
                board.putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.D, VerticalPosition.FOUR)))
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
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val board = Board.create()
            val newBoard = board.putDisk(
                player1.createMove(idGenerator, Position(HorizontalPosition.E, VerticalPosition.SIX))
            )
            val actual = newBoard.getDisk(Position(HorizontalPosition.E, VerticalPosition.SIX))
            assertEquals(Disk(DiskType.Dark), actual)
        }

        @Test
        @DisplayName("相手のディスクを裏返せない位置にディスクを置く事ができない")
        fun testPutDiskToPositionWithoutOpponentDisk() {
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val player2 = Player.createPlayer2(PlayerName("Player2"))
            val board = Board.create()
            val exception = assertThrows(CustomIllegalArgumentException::class.java) {
                board.putDisk(
                    player2.createMove(idGenerator, Position(HorizontalPosition.E, VerticalPosition.SIX))
                )
            }
            assertEquals("ディスクを置く事はできません", exception.message)
            assertEquals(
                "相手のディスクを裏返す事ができない位置にディスクを置くことはできません。PlayerMove(moveId=mockedId, number=PLAYER2, position=(E, SIX))",
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
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val board = Board.create()
            val newBoard = board.putDisk(
                player1.createMove(idGenerator, Position(HorizontalPosition.D, VerticalPosition.THREE))
            )
            assertAll(
                {
                    assertEquals(
                        Disk(DiskType.Dark),
                        newBoard.getDisk(Position(HorizontalPosition.D, VerticalPosition.THREE))
                    )
                },
                {
                    assertEquals(
                        Disk(DiskType.Dark),
                        newBoard.getDisk(Position(HorizontalPosition.D, VerticalPosition.FOUR))
                    )
                },
            )
        }

        @Test
        @DisplayName("水平方向に挟んだ相手のディスクを裏返す事ができる")
        fun testPutDiskToHorizontalPosition() {
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val board = Board.create()
            val newBoard = board.putDisk(
                player1.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.FIVE))
            )
            assertAll(
                {
                    assertEquals(
                        Disk(DiskType.Dark),
                        newBoard.getDisk(Position(HorizontalPosition.E, VerticalPosition.FIVE))
                    )
                },
                {
                    assertEquals(
                        Disk(DiskType.Dark),
                        newBoard.getDisk(Position(HorizontalPosition.F, VerticalPosition.FIVE))
                    )
                },
            )
        }

        @Test
        @DisplayName("斜め方向に挟んだ相手のディスクを裏返す事ができる")
        fun testPutDiskToDiagonalPosition() {
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val player2 = Player.createPlayer2(PlayerName("Player2"))
            val board = Board.create()
            val newBoard = board
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.SIX)))
            assertAll(
                {
                    assertEquals(
                        Disk(DiskType.Light),
                        newBoard.getDisk(Position(HorizontalPosition.E, VerticalPosition.FIVE))
                    )
                },
                {
                    assertEquals(
                        Disk(DiskType.Light),
                        newBoard.getDisk(Position(HorizontalPosition.F, VerticalPosition.SIX))
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
                Position(HorizontalPosition.C, VerticalPosition.FOUR),
                Position(HorizontalPosition.D, VerticalPosition.THREE),
                Position(HorizontalPosition.E, VerticalPosition.SIX),
                Position(HorizontalPosition.F, VerticalPosition.FIVE),
            )
            assertEquals(expected, actual)
        }

        @Test
        @DisplayName("１手進めた盤目に配置可能な全てのSquareを取得する事ができる")
        fun testGetAllPuttableSquaresAfterOneMove() {
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val board = Board.create()
            val newBoard = board
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.FIVE)))
            val actual = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER2)
            val expected = listOf(
                Position(HorizontalPosition.D, VerticalPosition.SIX),
                Position(HorizontalPosition.F, VerticalPosition.FOUR),
                Position(HorizontalPosition.F, VerticalPosition.SIX),
            )
            assertEquals(expected, actual)
        }

        @Test
        @DisplayName("配置可能なSquareがない場合は空リストを返す（黒が勝つパターン）")
        fun testGetAllPuttableSquaresWhenNoPuttableSquare() {
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val board = Board.create()
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val player2 = Player.createPlayer2(PlayerName("Player2"))
            val newBoard = board
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.D, VerticalPosition.SIX)))
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.C, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.FOUR)))
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.E, VerticalPosition.SEVEN)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.SIX)))
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.G, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.E, VerticalPosition.SIX)))
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.E, VerticalPosition.THREE)))
            val actualPlayer1 = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER1)
            val actualPlayer2 = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER2)
            assertAll(
                { assertEquals(emptyList<Position>(), actualPlayer1) },
                { assertEquals(emptyList<Position>(), actualPlayer2) },
            )
        }

        @Test
        @DisplayName("配置可能なSquareがない場合は空リストを返す（白が勝つパターン）")
        fun testGetAllPuttableSquaresWhenNoPuttableSquare2() {
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val board = Board.create()
            val player1 = Player.createPlayer1(PlayerName("Player1"))
            val player2 = Player.createPlayer2(PlayerName("Player2"))
            val newBoard = board
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.SIX)))
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.E, VerticalPosition.SIX)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.F, VerticalPosition.FOUR)))
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.E, VerticalPosition.THREE)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.D, VerticalPosition.TWO)))
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.D, VerticalPosition.THREE)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.D, VerticalPosition.SIX)))
                .putDisk(player1.createMove(idGenerator, Position(HorizontalPosition.C, VerticalPosition.FOUR)))
                .putDisk(player2.createMove(idGenerator, Position(HorizontalPosition.B, VerticalPosition.FOUR)))
            val actualPlayer1 = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER1)
            val actualPlayer2 = newBoard.getAllPuttableSquares(PlayerNumber.PLAYER2)
            assertAll(
                { assertEquals(emptyList<Position>(), actualPlayer1) },
                { assertEquals(emptyList<Position>(), actualPlayer2) },
            )
        }
    }
}