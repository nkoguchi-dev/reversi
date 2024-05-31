package org.koppepan.reversi.webapi

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.util.*


class TimeZoneExtension : BeforeAllCallback, AfterAllCallback {
    private lateinit var originalTimeZone: TimeZone
    override fun beforeAll(context: ExtensionContext?) {
        originalTimeZone = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    override fun afterAll(context: ExtensionContext?) {
        TimeZone.setDefault(originalTimeZone)
    }
}