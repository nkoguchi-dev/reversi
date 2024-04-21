package org.koppepan.demo.webapi.application.shared

inline fun requireOrThrow(value: Boolean, lazyMessage: () -> CustomExceptionMessage) {
    if (!value) {
        val message = lazyMessage()
        throw CustomIllegalArgumentException(
            message = message.message,
            description = message.description
        )
    }
}
