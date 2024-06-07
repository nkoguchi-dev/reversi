package org.koppepan.reversi.webapi

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer

@Configuration
@EnableR2dbcRepositories
class DatabaseConfig(
    @Value("\${spring.r2dbc.url}") private val r2dbcUrl: String,
) : AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory =
        ConnectionFactories.get(r2dbcUrl)

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        return initializer
    }
}