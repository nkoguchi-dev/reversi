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
        val postgresContainer = PostgreSQLContainer<Nothing>("postgres:16-alpine")
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
            "spring.r2dbc.url=r2dbc:postgresql://${postgresContainer.host}:${postgresContainer.firstMappedPort}/${postgresContainer.databaseName}",
            "spring.r2dbc.host=${postgresContainer.host}",
            "spring.r2dbc.port=${postgresContainer.firstMappedPort}",
            "spring.r2dbc.database=${postgresContainer.databaseName}",
            "spring.r2dbc.username=${postgresContainer.username}",
            "spring.r2dbc.password=${postgresContainer.password}"
        )
        values.applyTo(applicationContext)
    }
}