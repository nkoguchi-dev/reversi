package org.koppepan.reversi.webapi.infrastructure.entity

import org.komapper.annotation.*
import java.time.Instant

@KomapperEntity
@KomapperTable("reversi.moves")
data class MoveEntity(
    @KomapperId val gameId: String,
    @KomapperId val moveId: String,
    val playerNumber: String,
    val horizontalPosition: String,
    val verticalPosition: String,
    @KomapperCreatedAt val createdAt: Instant? = null,
    @KomapperUpdatedAt val updatedAt: Instant? = null,
)