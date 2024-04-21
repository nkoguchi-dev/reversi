package org.koppepan.demo.webapi.domain.generator

fun interface IdGenerator {
    fun generate(): String
}