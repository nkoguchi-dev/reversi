package org.koppepan.demo.webapi

import org.flywaydb.core.Flyway
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

class PostgreSQLTestContainersExtension : BeforeEachCallback,
    ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        @Container
        val postgresContainer = PostgreSQLContainer<Nothing>("postgres:12-alpine")
            .apply {
                withDatabaseName("demodb")
                withUsername("pgadmin")
                withPassword("pgadmin")
                start()
            }
    }

    override fun beforeEach(context: ExtensionContext?) {
        Flyway.configure().dataSource(
            postgresContainer.jdbcUrl,
            postgresContainer.username,
            postgresContainer.password,
        ).schemas("demo").load().migrate()
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val values = TestPropertyValues.of(
            "spring.datasource.url=${postgresContainer.jdbcUrl}",
            "spring.datasource.username=${postgresContainer.username}",
            "spring.datasource.password=${postgresContainer.password}"
        )
        values.applyTo(applicationContext)
    }
}