package com.github.tacascer.predix.user

import io.kotest.core.spec.style.FunSpec
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

internal class UserControllerTest : FunSpec({
    val client: WebTestClient = WebTestClient.bindToController(UserController()).configureClient().baseUrl("/users").build()
    test("getUsers should return a list of users") {
        client.get().accept(MediaType.APPLICATION_JSON).exchange()
            .expectStatus().isOk
            .expectBodyList(User::class.java)
            .hasSize(2)
            .contains(User.of("tacascer"), User.of("tacascer2"))
    }
})
