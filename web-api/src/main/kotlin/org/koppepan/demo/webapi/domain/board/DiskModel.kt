package org.koppepan.demo.webapi.domain.board

/**
 * ディスク（駒）を表すクラス
 */
class Disk(
    val diskType: DiskType
) {
    private fun copy(diskType: DiskType): Disk = Disk(diskType)

    fun reverse(): Disk = when (diskType) {
        DiskType.Light -> this.copy(diskType = DiskType.Dark)
        DiskType.Dark -> this.copy(diskType = DiskType.Light)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Disk) {
            return false
        }
        if (diskType != other.diskType) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        return diskType.hashCode()
    }

    override fun toString(): String {
        return "Disk(diskType=$diskType)"
    }
}

enum class DiskType {
    Light,
    Dark,
}