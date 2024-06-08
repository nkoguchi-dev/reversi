package org.koppepan.reversi.webapi.domain.shared

inline fun requireOrThrow(value: Boolean, lazyMessage: () -> ExceptionMessage) {
    if (!value) {
        val message = lazyMessage()
        throw IllegalArgumentDomainException(
            message = message.message,
            description = message.description
        )
    }
}
