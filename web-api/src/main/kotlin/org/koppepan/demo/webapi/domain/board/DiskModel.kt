package org.koppepan.demo.webapi.domain.board

/**
 * ディスク（駒）を表すクラス
 */
data class Disk(
    val diskType: DiskType
) {
    fun reverse(): Disk = when (diskType) {
        DiskType.Light -> this.copy(diskType = DiskType.Dark)
        DiskType.Dark -> this.copy(diskType = DiskType.Light)
    }
}

enum class DiskType {
    Light,
    Dark,
}