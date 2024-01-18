package com.github.tacascer.predix.config

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayProperties
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(R2dbcProperties::class, FlywayProperties::class)
class FlywayConfig {
    @Bean(initMethod = "migrate")
    fun flyway(r2dbcProperties: R2dbcProperties, flywayProperties: FlywayProperties): Flyway {
        return Flyway.configure()
            .dataSource(r2dbcProperties.url, r2dbcProperties.username, r2dbcProperties.password)
            .locations(*flywayProperties.locations.toTypedArray())
            .baselineOnMigrate(true)
            .load()
    }
}