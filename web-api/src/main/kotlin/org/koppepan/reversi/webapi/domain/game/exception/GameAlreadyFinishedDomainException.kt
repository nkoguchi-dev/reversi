package org.koppepan.reversi.webapi.domain.game.exception

import org.koppepan.reversi.webapi.domain.shared.IllegalArgumentDomainException

class GameAlreadyFinishedDomainException(
    message: String,
    description: String,
) : IllegalArgumentDomainException(
    message = message,
    description = description
)