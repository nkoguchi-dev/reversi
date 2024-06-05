package org.koppepan.reversi.webapi.domain.game

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.koppepan.reversi.webapi.domain.board.*
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
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
        val expected = GameStatus(
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
        )
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("駒を置いてゲームを進行できること")
    fun putDisk() {
        whenever(idGenerator.generate())
            .thenReturn("gameId")

        val game = Game.start(
            idGenerator = idGenerator,
            player1Name = "player1",
            player2Name = "player2",
        )

        val nextGame = game.putDisk(
            PlayerMove(
                PlayerNumber.PLAYER1,
                SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE),
                Disk(DiskType.Dark),
            )
        )

        val actual = nextGame.getGameStatus()
        val expected = GameStatus(
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
        )
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("自分の順番ではないプレイヤーが駒を置くとエラーが発生すること")
    fun putDiskWhenNotYourTurn() {
        whenever(idGenerator.generate())
            .thenReturn("gameId")

        val game = Game.start(
            idGenerator = idGenerator,
            player1Name = "player1",
            player2Name = "player2",
        )

        val actual = assertThrows<CustomIllegalArgumentException> {
            game.putDisk(
                PlayerMove(
                    PlayerNumber.PLAYER2,
                    SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE),
                    Disk(DiskType.Light),
                )
            )
        }
        val expected = CustomIllegalArgumentException(
            message = "ディスクを置く事はできません",
            description = "自分の順番ではないプレイヤーが駒を置くことはできません。playerMove: PlayerMove(number=PLAYER2, position=(F, FIVE), disk=Disk(diskType=Light)), nextPlayerNumber: PLAYER1",
        )
        assertAll(
            { assertEquals(expected.message, actual.message) },
            { assertEquals(expected.description, actual.description) },
        )
    }
}