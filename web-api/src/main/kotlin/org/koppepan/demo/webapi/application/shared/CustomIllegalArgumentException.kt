package org.koppepan.demo.webapi.application.shared

class CustomIllegalArgumentException(
    message: String,
    val description: String,
    cause: Throwable? = null
) : Throwable(
    message,
    cause
)