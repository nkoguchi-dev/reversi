package org.koppepan.demo.webapi.domain.board

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koppepan.demo.webapi.domain.shared.CustomIllegalArgumentException

class DiskTest {
    @Test
    @DisplayName("DiskIdのインスタンスを生成できること")
    fun testDiskId() {
        val diskId = DiskId("test")
        assertEquals("test", diskId.value)
    }

    @Test
    @DisplayName("空文字のDiskIdのインスタンスを生成すると例外が発生すること")
    fun testDiskIdEmpty() {
        val exception = assertThrows<CustomIllegalArgumentException> {
            DiskId("")
        }
        assertAll(
            { assertEquals("DiskIdに空文字を設定する事はできません", exception.message) },
            { assertEquals("", exception.description) },
        )
    }

    @Test
    @DisplayName("Diskクラスのインスタンスを生成できること")
    fun testDisk() {
        val disk = Disk(DiskId("test"), DiskType.Light)
        assertAll(
            { assertEquals("test", disk.diskId.value) },
            { assertEquals(DiskType.Light, disk.diskType) },
        )
    }

    @Test
    @DisplayName("Diskクラスのreverseメソッドが正常に動作すること")
    fun testDiskReverse() {
        val disk = Disk(DiskId("test"), DiskType.Light)
        val reversedDisk = disk.reverse()
        assertAll(
            { assertEquals("test", reversedDisk.diskId.value) },
            { assertEquals(DiskType.Dark, reversedDisk.diskType) },
        )
    }
}