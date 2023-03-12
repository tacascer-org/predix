package com.github.tacascer.predix

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter

private val handler = HelloHandler()

@Configuration
class HelloRouter {
    @Bean
    fun route() = coRouter {
        accept(
            APPLICATION_JSON
        ).nest {
            GET("/hello", handler::hello)
        }
    }
}

class HelloHandler {
    suspend fun hello(request: ServerRequest): ServerResponse {
        return ok().bodyValueAndAwait("Hello, world")
    }
}