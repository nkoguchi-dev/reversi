package org.koppepan.demo.webapi.domain.board

import org.koppepan.demo.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.demo.webapi.domain.shared.requireOrThrow
import org.koppepan.demo.webapi.domain.generator.IdGenerator

data class Disk(
    val diskId: DiskId,
    val diskType: DiskType
) {
    fun reverse(): Disk = when (diskType) {
        DiskType.Light -> this.copy(diskType = DiskType.Dark)
        DiskType.Dark -> this.copy(diskType = DiskType.Light)
    }
}

data class DiskId(
    val value: String
) {
    init {
        requireOrThrow(value.isNotBlank()) {
            CustomExceptionMessage(
                message = "DiskIdに空文字を設定する事はできません",
                description = "",
            )
        }
    }

    companion object {
        fun generate(idGenerator: IdGenerator): DiskId = DiskId(idGenerator.generate())
    }
}

enum class DiskType {
    Light,
    Dark,
}