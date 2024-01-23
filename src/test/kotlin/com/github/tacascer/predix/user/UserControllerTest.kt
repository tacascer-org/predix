package com.github.tacascer.predix.user

import com.github.tacascer.predix.user.utils.shouldBeSameAs
import com.github.tacascer.predix.user.utils.userModel
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
        val user = Instancio.of(userModel()).create()

        coEvery {
            userService.findById(user.id)
        } returns user

        client.get().uri("/${user.id}").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk.expectBody(
            User::class.java
        ).value {
            it shouldBeEqual user
        }

        coVerify(exactly = 1) { userService.findById(user.id) }
    }

    test("addUser should add a user") {
        val user = Instancio.of(userModel()).create()

        coEvery {
            userService.add(user)
        } returns user

        client.post().uri("/").accept(MediaType.APPLICATION_JSON).bodyValue(user).exchange()
            .expectStatus().isCreated.expectBody(User::class.java).value {
                it shouldBeEqual user
            }

        coVerify(exactly = 1) { userService.add(user) }
    }

    test("addUserEvent should add a user event") {
        val userId = Instancio.create(UserId::class.java)
        val userEventCreationDTO = Instancio.create(UserEventCreationDTO::class.java)
        val userEvent = UserEvent.of(userEventCreationDTO.title, userEventCreationDTO.description, userId)

        coEvery {
            userService.addEvent(any())
        } returns userEvent

        client.post().uri("/${userId}/events").accept(MediaType.APPLICATION_JSON).bodyValue(userEventCreationDTO)
            .exchange()
            .expectStatus().isCreated.expectBody(UserEvent::class.java).value {
                it shouldBeSameAs userEvent
            }
        coVerify(exactly = 1) { userService.addEvent(any()) }
    }

    afterTest {
        clearMocks(userService)
    }

})
