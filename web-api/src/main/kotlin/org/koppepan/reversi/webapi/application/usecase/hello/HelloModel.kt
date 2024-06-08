package org.koppepan.reversi.webapi.application.usecase.hello

import org.koppepan.reversi.webapi.domain.shared.ExceptionMessage
import org.koppepan.reversi.webapi.domain.shared.requireOrThrow


data class HelloName(
    val value: String
) {
    init {
        requireOrThrow(value.isNotBlank()) {
            ExceptionMessage(
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
            ExceptionMessage(
                message = "メッセージを設定してください",
                description = "HelloMessageは空の値を設定する事はできません",
            )
        }
    }
}

