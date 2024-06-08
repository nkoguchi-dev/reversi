package org.koppepan.reversi.webapi.game

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
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
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody<CreateGameController.CreateGameResponse>()
            .returnResult()

        val expected = CreateGameController.CreateGameResponse(
            gameId = response.responseBody?.gameId!!,
            player1Name = "ペンギン",
            player2Name = "パンダ",
            nextPlayer = "PLAYER1",
            progress = "CREATED",
            diskMap = mapOf(
                "D:4" to "LIGHT",
                "D:5" to "DARK",
                "E:4" to "DARK",
                "E:5" to "LIGHT",
            )
        )
        assertEquals(expected, response.responseBody)

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

    @Test
    @DisplayName("プレイヤー１の名前に空文字を指定してゲームを開始するとBadRequestが返却されること")
    fun testStartWithEmptyPlayerName() = runTest {
        webTestClient
            .post()
            .uri("/api/games/start")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "player1": "",
                    "player2": "パンダ"
                }
                """.trimIndent()
            )
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .json(
                """
                {
                    "message": "プレイヤー名を作成する事ができません",
                    "description": "プレイヤー名は必ず指定してください"
                }
                """.trimIndent()
            )
    }

    @Test
    @DisplayName("プレイヤー２の名前に空文字を指定してゲームを開始するとBadRequestが返却されること")
    fun testStartWithEmptyPlayer2Name() = runTest {
        webTestClient
            .post()
            .uri("/api/games/start")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "player1": "ペンギン",
                    "player2": ""
                }
                """.trimIndent()
            )
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .json(
                """
                {
                    "message": "プレイヤー名を作成する事ができません",
                    "description": "プレイヤー名は必ず指定してください"
                }
                """.trimIndent()
            )
    }

    @Test
    @DisplayName("プレイヤー１の名前に200文字以上を指定してゲームを開始するとBadRequestが返却されること")
    fun testStartWithTooLongPlayerName() = runTest {
        webTestClient
            .post()
            .uri("/api/games/start")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "player1": "${"a".repeat(201)}",
                    "player2": "パンダ"
                }
                """.trimIndent()
            )
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .json(
                """
                {
                    "message": "プレイヤー名を作成する事ができません",
                    "description": "プレイヤー名は200文字以内で指定してください"
                }
                """.trimIndent()
            )
    }

    @Test
    @DisplayName("プレイヤー２の名前に200文字以上を指定してゲームを開始するとBadRequestが返却されること")
    fun testStartWithTooLongPlayer2Name() = runTest {
        webTestClient
            .post()
            .uri("/api/games/start")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "player1": "ペンギン",
                    "player2": "${"a".repeat(201)}"
                }
                """.trimIndent()
            )
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .json(
                """
                {
                    "message": "プレイヤー名を作成する事ができません",
                    "description": "プレイヤー名は200文字以内で指定してください"
                }
                """.trimIndent()
            )
    }

}