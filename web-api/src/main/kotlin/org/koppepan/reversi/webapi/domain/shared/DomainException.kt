package org.koppepan.reversi.webapi.domain.shared

open class DomainException(
    message: String,
    val description: String,
    cause: Throwable? = null
) : Throwable(
    message,
    cause
)