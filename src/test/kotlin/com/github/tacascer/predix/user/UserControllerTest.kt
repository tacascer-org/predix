package com.github.tacascer.predix.user

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.instancio.Instancio
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

internal class UserControllerTest : FunSpec({
    val userService = mockk<UserService>()

    val client: WebTestClient =
        WebTestClient.bindToController(UserController(userService)).configureClient().baseUrl("/users").build()

    test("getUsers should return a list of users") {
        val user = Instancio.create(User::class.java)

        coEvery {
            userService.findById(user.id)
        } returns user

        client.get().uri("/${user.id}").accept(MediaType.APPLICATION_JSON).exchange()
            .expectStatus().isOk
            .expectBody(User::class.java)
            .value {
                it shouldBeEqual user
            }

        coVerify(exactly = 1) { userService.findById(user.id) }
    }

    afterTest {
        clearMocks(userService)
    }
})
