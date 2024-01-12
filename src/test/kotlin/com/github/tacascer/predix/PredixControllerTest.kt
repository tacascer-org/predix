package com.github.tacascer.predix

import io.kotest.core.spec.style.FunSpec
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

internal class PredixControllerTest : FunSpec({
    val client: WebTestClient = WebTestClient.bindToController(PredixController()).build()
    test("getPredix should return Hello world") {
        client.get().uri("/").accept(MediaType.APPLICATION_JSON).exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Hello world")
    }
    test("getUsers should return a list of users") {
        client.get().uri("/users").accept(MediaType.APPLICATION_JSON).exchange()
            .expectStatus().isOk
            .expectBodyList(User::class.java)
            .hasSize(2)
            .contains(User("tacascer"), User("tacascer2"))
    }
})
