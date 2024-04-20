package org.koppepan.demo.webapi.application.usecase.shared

class CustomIllegalArgumentException(
    message: String,
    val description: String,
    cause: Throwable? = null
) : Throwable(
    message,
    cause
)