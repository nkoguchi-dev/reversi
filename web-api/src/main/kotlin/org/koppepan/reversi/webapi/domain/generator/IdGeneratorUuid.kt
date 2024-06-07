package org.koppepan.reversi.webapi.domain.generator

import org.springframework.stereotype.Component
import java.util.*

@Component
class IdGeneratorUuid : IdGenerator {
    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}