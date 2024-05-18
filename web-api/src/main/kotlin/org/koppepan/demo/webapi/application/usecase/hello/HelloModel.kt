package org.koppepan.demo.webapi.application.usecase.hello

import org.koppepan.demo.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.demo.webapi.domain.shared.requireOrThrow

data class HelloName(
    val value: String
) {
    init {
        requireOrThrow(value.isNotBlank()) {
            CustomExceptionMessage(
                message = "名前を設定してください",
                description = "HelloNameは空の値を設定する事はできません",
            )
        }
    }
}

data class HelloMessage(
    val value: String
) {
    init {
        requireOrThrow(value.isNotBlank()) {
            CustomExceptionMessage(
                message = "メッセージを設定してください",
                description = "HelloMessageは空の値を設定する事はできません",
            )
        }
    }
}
