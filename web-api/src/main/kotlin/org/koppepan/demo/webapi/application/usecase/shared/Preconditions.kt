package org.koppepan.demo.webapi.application.usecase.shared

inline fun require(value: Boolean, lazyMessage: () -> CustomExceptionMessage) {
    if (!value) {
        val message = lazyMessage()
        throw CustomIllegalArgumentException(
            message = message.message,
            description = message.description
        )
    }
}
