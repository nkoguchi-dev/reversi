package org.koppepan.demo.webapi.domain.generator

class IdGeneratorUuid : IdGenerator {
    override fun generate(): String {
        return java.util.UUID.randomUUID().toString()
    }
}