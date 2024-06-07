package org.koppepan.reversi.webapi

import io.r2dbc.spi.ConnectionFactoryOptions
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class KomapperConfig {
    @Bean
    fun database(client: DatabaseClient): R2dbcDatabase {
        val option = ConnectionFactoryOptions.builder()
            .option(ConnectionFactoryOptions.DRIVER, "h2")
            .option(ConnectionFactoryOptions.PROTOCOL, "mem")
            .option(ConnectionFactoryOptions.DATABASE, "reversi_db")
            .option(ConnectionFactoryOptions.USER, "reversi_user")
            .option(ConnectionFactoryOptions.PASSWORD, "reversi_user")
            .build()
        return R2dbcDatabase(option)
    }
}