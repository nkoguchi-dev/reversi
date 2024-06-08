package org.koppepan.reversi.webapi.domain.shared

/**
 * ドメインモデルを作成する際に利用する値が不正である場合にスローされる例外
 */
open class IllegalArgumentDomainException(
    message: String,
    description: String,
) : DomainException(
    message,
    description
)