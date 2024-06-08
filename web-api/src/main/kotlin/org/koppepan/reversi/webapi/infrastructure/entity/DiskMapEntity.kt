package org.koppepan.reversi.webapi.infrastructure.entity

import org.komapper.annotation.*
import java.time.Instant

@KomapperEntity
@KomapperTable("reversi.disk_maps")
data class DiskMapEntity(
    @KomapperId val gameId: String,
    @KomapperId val horizontalPosition: String,
    @KomapperId val verticalPosition: String,
    val diskType: String,
    @KomapperCreatedAt val createdAt: Instant? = null,
    @KomapperUpdatedAt val updatedAt: Instant? = null,
)