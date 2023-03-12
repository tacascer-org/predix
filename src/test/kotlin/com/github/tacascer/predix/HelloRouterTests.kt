package com.github.tacascer.predix

import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

class HelloRouterTests {
    private val client = WebTestClient.bindToRouterFunction(HelloRouter().route()).build()

    @Test
    fun `hello returns Hello, world`() {
        client.get().uri("/hello").exchange().expectStatus().isOk.expectBody<String>().isEqualTo("Hello, world")
    }
}