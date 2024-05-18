package org.koppepan.demo.webapi.domain.board

import org.koppepan.demo.webapi.domain.shared.CustomExceptionMessage
import org.koppepan.demo.webapi.domain.shared.requireOrThrow
import org.koppepan.demo.webapi.domain.generator.IdGenerator

/**
 * 盤を表すクラス
 */
data class Board(
    val boardId: BoardId,
    val squareLines: List<SquareLine>,
)

data class BoardId(
    val value: String,
) {
    init {
        requireOrThrow(value.isNotBlank()) {
            CustomExceptionMessage(
                message = "BoardIdに空文字を設定する事はできません",
                description = "",
            )
        }
    }

    companion object {
        fun generate(idGenerator: IdGenerator): BoardId = BoardId(idGenerator.generate())
    }
}