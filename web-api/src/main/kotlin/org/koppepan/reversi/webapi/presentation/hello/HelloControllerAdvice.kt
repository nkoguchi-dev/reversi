package org.koppepan.reversi.webapi.presentation.hello

import org.koppepan.reversi.webapi.domain.shared.IllegalArgumentDomainException
import org.koppepan.reversi.webapi.presentation.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice("org.koppepan.reversi.webapi.presentation.hello")
class HelloControllerAdvice {
    @ExceptionHandler(IllegalArgumentDomainException::class)
    fun handleCustomIllegalArgumentException(e: IllegalArgumentDomainException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                    message = e.message ?: "Bad Request",
                    description = e.description,
                )
            )
    }
}