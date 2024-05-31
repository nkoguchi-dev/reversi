package org.koppepan.reversi.webapi.domain.generator

fun interface IdGenerator {
    fun generate(): String
}