package org.koppepan.reversi.webapi.application.usecase.game.shared

open class ApplicationException(
    message: String,
    val description: String,
    cause: Throwable? = null
) : Throwable(
    message,
    cause
)