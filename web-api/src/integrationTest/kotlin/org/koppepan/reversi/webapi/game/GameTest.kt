package org.koppepan.reversi.webapi.game

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.koppepan.reversi.webapi.IntegrationTest
import org.koppepan.reversi.webapi.infrastructure.entity.DiskMapEntity
import org.koppepan.reversi.webapi.infrastructure.entity.GameEntity
import org.koppepan.reversi.webapi.infrastructure.entity.diskMapEntity
import org.koppepan.reversi.webapi.infrastructure.entity.gameEntity
import org.koppepan.reversi.webapi.presentation.game.create.CreateGameController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@IntegrationTest
class GameTest(
    @Autowired private var webTestClient: WebTestClient,
    @Autowired private val db: R2dbcDatabase,
) {
    @Test
    @DisplayName("正常にゲームを開始できること")
    fun testStart() = runTest {
        val response = webTestClient
            .post()
            .uri("/api/games/start")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "player1": "ペンギン",
                    "player2": "パンダ"
                }
                """.trimIndent()
            )
            .exchange()
            .expectStatus().isOk
            .expectBody<CreateGameController.CreateGameResponse>()
            .returnResult()

        assertNotNull(response.responseBody?.gameId)

        // ゲーム情報が正しく永続化されている事の確認
        // TODO: 状態取得APIを作成した後はDBを直接確認するのではなく状態取得APIを用いて検証する
        val gameId = response.responseBody?.gameId
        val getGameQuery = QueryDsl
            .from(Meta.gameEntity)
            .where { Meta.gameEntity.gameId eq gameId }
            .singleOrNull()
        val actualGameEntity = db.runQuery(getGameQuery)
            ?.copy(createdAt = null, updatedAt = null)
        val expectedGameEntity = GameEntity(
            gameId = gameId!!,
            player1Name = "ペンギン",
            player2Name = "パンダ",
            status = "CREATED",
        )
        assertEquals(expectedGameEntity, actualGameEntity)

        // DiskMap情報が正しく永続化されている事の確認
        val getDiskMapQuery = QueryDsl
            .from(Meta.diskMapEntity)
            .where { Meta.diskMapEntity.gameId eq gameId }
            .orderBy(Meta.diskMapEntity.horizontalPosition, Meta.diskMapEntity.verticalPosition)
        val actualDiskMapSet = db.runQuery(getDiskMapQuery)
            .map { it.copy(createdAt = null, updatedAt = null) }
        val expectedDiskMapSet = listOf(
            DiskMapEntity(gameId = gameId, horizontalPosition = "D", verticalPosition = "4", diskType = "LIGHT"),
            DiskMapEntity(gameId = gameId, horizontalPosition = "D", verticalPosition = "5", diskType = "DARK"),
            DiskMapEntity(gameId = gameId, horizontalPosition = "E", verticalPosition = "4", diskType = "DARK"),
            DiskMapEntity(gameId = gameId, horizontalPosition = "E", verticalPosition = "5", diskType = "LIGHT"),
        )
        assertEquals(expectedDiskMapSet, actualDiskMapSet)
    }
}