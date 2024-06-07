package org.koppepan.reversi.webapi.domain.board

/**
 * ディスク（駒）を表すクラス
 */
class Disk(
    val type: DiskType
) {
    private fun copy(diskType: DiskType): Disk = Disk(diskType)

    fun reverse(): Disk = when (type) {
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
        if (type != other.type) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }

    override fun toString(): String {
        return "Disk(diskType=$type)"
    }
}

enum class DiskType(val value: String) {
    Dark("DARK"),
    Light("LIGHT"),
}