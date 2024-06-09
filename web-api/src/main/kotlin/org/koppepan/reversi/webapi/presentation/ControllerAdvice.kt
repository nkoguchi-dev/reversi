package org.koppepan.reversi.webapi.presentation

import org.koppepan.reversi.webapi.domain.shared.IllegalArgumentDomainException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice("org.koppepan.reversi.webapi.presentation")
class ControllerAdvice {
    @ExceptionHandler(IllegalArgumentDomainException::class)
    fun handleAccessNotAllowedException(e: IllegalArgumentDomainException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    message = e.message ?: "Bad Request",
                    description = e.description,
                )
            )
}