package org.koppepan.reversi.webapi.domain.game

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koppepan.reversi.webapi.domain.board.Board
import org.koppepan.reversi.webapi.domain.generator.IdGenerator
import org.koppepan.reversi.webapi.domain.player.Player
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class GameTest {
    @Mock
    private lateinit var idGenerator: IdGenerator

    @Test
    @DisplayName("ゲームを開始できること")
    fun startGame() {
        whenever(idGenerator.generate())
            .thenReturn("gameId")

        val actual = Game.start(
            idGenerator,
            listOf(
                Player("player1"),
                Player("player2"),
            )
        )

        val expected = Game.recreate(
            GameId.recreate("gameId"),
            Board.create(),
            listOf(
                Player("player1"),
                Player("player2"),
            )
        )

        assertEquals(expected, actual)
    }

}