package org.koppepan.reversi.webapi.presentation.hello

import org.koppepan.reversi.webapi.domain.shared.CustomIllegalArgumentException
import org.koppepan.reversi.webapi.presentation.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice("org.koppepan.reversi.webapi.presentation.hello")
class HelloControllerAdvice {
    @ExceptionHandler(CustomIllegalArgumentException::class)
    fun handleCustomIllegalArgumentException(e: CustomIllegalArgumentException): ResponseEntity<ErrorResponse> {
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