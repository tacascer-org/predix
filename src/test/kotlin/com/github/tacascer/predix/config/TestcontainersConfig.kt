package com.github.tacascer.predix.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@TestConfiguration(proxyBeanMethods = false)
@Testcontainers
class TestcontainersConfig {
    @Bean
    @ServiceConnection
    fun postgresContainer() = PostgreSQLContainer("postgres:16")
}