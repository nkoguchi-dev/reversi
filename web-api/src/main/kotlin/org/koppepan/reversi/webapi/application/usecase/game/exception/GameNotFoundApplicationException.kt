package org.koppepan.reversi.webapi.application.usecase.game.exception

import org.koppepan.reversi.webapi.application.usecase.game.shared.ApplicationException

/**
 * 指定したGameが見つからない場合にスローされる例外
 */
open class GameNotFoundApplicationException(
    message: String,
    description: String,
) : ApplicationException(
    message,
    description
)