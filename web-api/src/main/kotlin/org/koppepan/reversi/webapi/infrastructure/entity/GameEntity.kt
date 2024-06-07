package org.koppepan.reversi.webapi.infrastructure.entity

import org.komapper.annotation.*
import java.time.Instant

@KomapperEntity
@KomapperTable("reversi.games")
data class GameEntity(
    @KomapperId val gameId: String,
    val player1Name: String,
    val player2Name: String,
    @KomapperCreatedAt val createdAt: Instant,
    @KomapperUpdatedAt val updatedAt: Instant,
)