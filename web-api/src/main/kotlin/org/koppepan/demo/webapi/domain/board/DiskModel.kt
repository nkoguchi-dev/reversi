package org.koppepan.demo.webapi.domain.board

/**
 * ディスク（駒）を表すクラス
 */
class Disk private constructor(
    val diskType: DiskType
) {
    companion object {
        fun create(type: DiskType): Disk = Disk(type)
    }

    private fun copy(diskType: DiskType): Disk = Disk(diskType)

    fun reverse(): Disk = when (diskType) {
        DiskType.Light -> this.copy(diskType = DiskType.Dark)
        DiskType.Dark -> this.copy(diskType = DiskType.Light)
    }
}

enum class DiskType {
    Light,
    Dark,
}