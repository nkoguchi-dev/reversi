package org.koppepan.reversi.webapi.domain.game

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.koppepan.reversi.webapi.domain.board.*
import org.koppepan.reversi.webapi.domain.game.exception.GameAlreadyFinishedException
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.Player
import org.koppepan.reversi.webapi.domain.player.PlayerName
import org.koppepan.reversi.webapi.domain.player.PlayerNumber
import org.koppepan.reversi.webapi.domain.shared.CustomIllegalArgumentException
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class GameTest {
    @Mock
    private lateinit var idGenerator: IdGenerator

    @Test
    @DisplayName("ゲームを開始できること")
    fun getGameStatus() {
        whenever(idGenerator.generate())
            .thenReturn("gameId")

        val game = Game.start(
            idGenerator = idGenerator,
            player1Name = "player1",
            player2Name = "player2",
        )

        val actual = game.getGameStatus()
        val expected = GameState(
            GameId.recreate("gameId"),
            PlayerName("player1"),
            PlayerName("player2"),
            DiskMap.of(
                SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Light),
                SquarePosition(HorizontalPosition.E, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                SquarePosition(HorizontalPosition.D, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
            ),
            PlayerNumber.PLAYER1,
            GameProgress.CREATED,
        )
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("駒を置いてゲームを進行できること")
    fun putDisk() {
        whenever(idGenerator.generate()).thenReturn("gameId")
        val player1 = Player.createPlayer1(PlayerName("player1"))
        val game = Game.start(
            idGenerator = idGenerator,
            player1Name = "player1",
            player2Name = "player2",
        )

        val nextGame = game
            .putDisk(player1.createMove(idGenerator, SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE)))

        val actual = nextGame.getGameStatus()
        val expected = GameState(
            gameId = GameId.recreate("gameId"),
            player1Name = PlayerName("player1"),
            player2Name = PlayerName("player2"),
            diskMap = DiskMap.of(
                SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Light),
                SquarePosition(HorizontalPosition.E, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                SquarePosition(HorizontalPosition.D, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE) to Disk(DiskType.Dark),
            ),
            nextPlayerNumber = PlayerNumber.PLAYER2,
            progress = GameProgress.IN_PROGRESS,
        )
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("自分の順番ではないプレイヤーが駒を置くとエラーが発生すること")
    fun putDiskWhenNotYourTurn() {
        whenever(idGenerator.generate()).thenReturn("mockedId")
        val player2 = Player.createPlayer2(PlayerName("player2"))
        val game = Game.start(
            idGenerator = idGenerator,
            player1Name = "player1",
            player2Name = "player2",
        )

        val actual = assertThrows<CustomIllegalArgumentException> {
            game.putDisk(player2.createMove(idGenerator, SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE)))
        }
        val expected = CustomIllegalArgumentException(
            message = "ディスクを置く事はできません",
            description = "自分の順番ではないプレイヤーが駒を置くことはできません。playerMove: PlayerMove(moveId=mockedId, number=PLAYER2, position=(F, FIVE)), nextPlayerNumber: PLAYER1",
        )
        assertAll(
            { assertEquals(expected.message, actual.message) },
            { assertEquals(expected.description, actual.description) },
        )
    }

    @Nested
    @DisplayName("ゲーム状態のテスト")
    inner class TestFromStartToFinish {
        @Test
        @DisplayName("作成しただけのゲームの状態を正しく出力できること")
        fun testFromStart() {
            whenever(idGenerator.generate()).thenReturn("gameId")
            val game = Game.start(idGenerator, "player1", "player2")
            val actual = game.getGameStatus()
            val expected = GameState(
                gameId = GameId.recreate("gameId"),
                player1Name = PlayerName("player1"),
                player2Name = PlayerName("player2"),
                diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Light),
                ),
                nextPlayerNumber = PlayerNumber.PLAYER1,
                progress = GameProgress.CREATED,
            )
            assertEquals(expected, actual)
        }

        @Test
        @DisplayName("ゲーム中の状態を正しく出力できること")
        fun testFromPlaying() {
            whenever(idGenerator.generate()).thenReturn("gameId")
            val game = Game.start(idGenerator, "player1", "player2")
            val player1 = game.player1
            val actual = game
                .putDisk(player1.createMove(idGenerator, SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE)))
                .getGameStatus()
            val expected = GameState(
                gameId = GameId.recreate("gameId"),
                player1Name = PlayerName("player1"),
                player2Name = PlayerName("player2"),
                diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Light),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                ),
                nextPlayerNumber = PlayerNumber.PLAYER2,
                progress = GameProgress.IN_PROGRESS,
            )
            assertEquals(expected, actual)
        }

        @Test
        @DisplayName("決着がついたゲームの状態を正しく出力できること")
        fun testFromFinish() {
            whenever(idGenerator.generate()).thenReturn("gameId")
            val game = Game.start(idGenerator, "player1", "player2")
            val player1 = game.player1
            val player2 = game.player2
            val actual = game
                .putDisk(player1.createMove(idGenerator, SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(idGenerator, SquarePosition(HorizontalPosition.D, VerticalPosition.SIX)))
                .putDisk(player1.createMove(idGenerator, SquarePosition(HorizontalPosition.C, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(idGenerator, SquarePosition(HorizontalPosition.F, VerticalPosition.FOUR)))
                .putDisk(player1.createMove(idGenerator, SquarePosition(HorizontalPosition.E, VerticalPosition.SEVEN)))
                .putDisk(player2.createMove(idGenerator, SquarePosition(HorizontalPosition.F, VerticalPosition.SIX)))
                .putDisk(player1.createMove(idGenerator, SquarePosition(HorizontalPosition.G, VerticalPosition.FIVE)))
                .putDisk(player2.createMove(idGenerator, SquarePosition(HorizontalPosition.E, VerticalPosition.SIX)))
                .putDisk(player1.createMove(idGenerator, SquarePosition(HorizontalPosition.E, VerticalPosition.THREE)))
                .getGameStatus()
            val expected = GameState(
                gameId = GameId.recreate("gameId"),
                player1Name = PlayerName("player1"),
                player2Name = PlayerName("player2"),
                diskMap = DiskMap.of(
                    SquarePosition(HorizontalPosition.C, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.D, VerticalPosition.SIX) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.THREE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.SIX) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.E, VerticalPosition.SEVEN) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.FOUR) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.F, VerticalPosition.SIX) to Disk(DiskType.Dark),
                    SquarePosition(HorizontalPosition.G, VerticalPosition.FIVE) to Disk(DiskType.Dark),
                ),
                nextPlayerNumber = PlayerNumber.PLAYER1,
                progress = GameProgress.FINISHED,
            )
            assertEquals(expected, actual)
        }

        @Test
        @DisplayName("決着がついたゲームで駒を置こうとした場合にエラーが発生すること")
        fun testPutDiskWhenFinished() {
            whenever(idGenerator.generate()).thenReturn("mockedId")
            val game = Game.recreate(
                gameId = GameId.recreate("gameId"),
                board = Board.create(),
                player1 = Player.createPlayer1(PlayerName("player1")),
                player2 = Player.createPlayer2(PlayerName("player2")),
                nextPlayerNumber = PlayerNumber.PLAYER1,
                progress = GameProgress.FINISHED,
            )
            val player1 = game.player1
            val actual = assertThrows<GameAlreadyFinishedException> {
                game.putDisk(player1.createMove(idGenerator, SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE)))
            }
            val expected = GameAlreadyFinishedException(
                message = "ディスクを置く事はできません",
                description = "ゲームが終了しているためディスクを配置することはできません。",
            )
            assertAll(
                { assertEquals(expected.message, actual.message) },
                { assertEquals(expected.description, actual.description) },
            )
        }
    }
}