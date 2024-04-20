package org.koppepan.demo.webapi.application.usecase.hello

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koppepan.demo.webapi.application.usecase.shared.CustomIllegalArgumentException

class HelloTest {
    @Test
    @DisplayName("HelloNameのインスタンスを作成できること")
    fun testHelloName() {
        val helloName = HelloName("John")
        assertEquals("John", helloName.value)
    }

    @Test
    @DisplayName("空のHelloNameのインスタンスは作成できないこと")
    fun testHelloNameEmpty() {
        val exception = assertThrows(CustomIllegalArgumentException::class.java) {
            HelloName("")
        }
        assertEquals("名前を設定してください", exception.message)
        assertEquals("HelloNameは空の値を設定する事はできません", exception.description)
    }

    @Test
    @DisplayName("HelloMessageのインスタンスを作成できること")
    fun testHelloMessage() {
        val helloMessage = HelloMessage("Hello, John!")
        assertEquals("Hello, John!", helloMessage.value)
    }

    @Test
    @DisplayName("空のHelloMessageのインスタンスは作成できないこと")
    fun testHelloMessageEmpty() {
        val exception = assertThrows(CustomIllegalArgumentException::class.java) {
            HelloMessage("")
        }
        assertEquals("メッセージを設定してください", exception.message)
        assertEquals("HelloMessageは空の値を設定する事はできません", exception.description)
    }
}