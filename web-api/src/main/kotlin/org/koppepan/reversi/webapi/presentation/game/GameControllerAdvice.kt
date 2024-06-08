package org.koppepan.reversi.webapi.presentation.game

import org.koppepan.reversi.webapi.domain.shared.IllegalArgumentDomainException
import org.koppepan.reversi.webapi.presentation.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice("org.koppepan.reversi.webapi.presentation.game")
class GameControllerAdvice {
    @ExceptionHandler(IllegalArgumentDomainException::class)
    fun handleAccessNotAllowedException(e: IllegalArgumentDomainException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    message = e.message ?: "Bad Request",
                    description = e.description,
                )
            )
    }
}