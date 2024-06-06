package org.koppepan.reversi.webapi.domain.game.exception

import org.koppepan.reversi.webapi.domain.shared.CustomIllegalArgumentException

class GameAlreadyFinishedException(
    message: String,
    description: String,
) : CustomIllegalArgumentException(
    message = message,
    description = description
)