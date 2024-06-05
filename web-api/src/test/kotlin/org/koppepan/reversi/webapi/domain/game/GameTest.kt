package org.koppepan.reversi.webapi.domain.game

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koppepan.reversi.webapi.domain.board.*
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.PlayerName
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.test.context.ActiveProfiles

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
            PlayerName("player1"),
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
            PlayerName("player1"),
            SquarePosition(HorizontalPosition.F, VerticalPosition.FIVE),
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
            nextPlayerName = PlayerName("player2"),
        )
        assertEquals(expected, actual)
    }
}