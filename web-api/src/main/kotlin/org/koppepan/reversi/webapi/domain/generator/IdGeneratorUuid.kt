package org.koppepan.reversi.webapi.domain.generator

import java.util.*

class IdGeneratorUuid : IdGenerator {
    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}