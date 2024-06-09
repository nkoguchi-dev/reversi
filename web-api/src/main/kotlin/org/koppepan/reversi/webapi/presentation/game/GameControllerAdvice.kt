package org.koppepan.reversi.webapi.presentation.game

import org.koppepan.reversi.webapi.application.usecase.game.exception.GameNotFoundApplicationException
import org.koppepan.reversi.webapi.presentation.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice("org.koppepan.reversi.webapi.presentation.game")
class GameControllerAdvice {
    @ExceptionHandler(GameNotFoundApplicationException::class)
    fun handleGameNotFoundApplicationException(e: GameNotFoundApplicationException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    message = e.message ?: "Game Not Found",
                    description = e.description,
                )
            )
}