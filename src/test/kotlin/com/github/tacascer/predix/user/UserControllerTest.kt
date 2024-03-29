package com.github.tacascer.predix.user

import com.github.tacascer.predix.event.EventId
import com.github.tacascer.predix.instancio.field
import com.github.tacascer.predix.user.dto.*
import com.github.tacascer.predix.user.utils.userEventModel
import com.github.tacascer.predix.user.utils.userModel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import org.instancio.Instancio
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

internal class UserControllerTest : FunSpec({
    val userService = mockk<UserService>()

    val client: WebTestClient =
        WebTestClient.bindToController(UserController(userService)).configureClient().baseUrl("/users").build()

    test("when user is not found, then getUser should return not found") {
        val userId = Instancio.create(UserId::class.java)

        coEvery {
            userService.findById(userId)
        } returns null

        client.get().uri("/${userId}").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isNotFound

        coVerify(exactly = 1) { userService.findById(userId) }
    }

    test("when user is found, then getUser should return a user") {
        val user = Instancio.of(userModel()).create()
        val userDTO = user.toUserDTO()

        coEvery {
            userService.findById(user.id)
        } returns user

        client.get().uri("/${user.id}").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk.expectBody(
            UserDTO::class.java
        ).value {
            it shouldBeEqual userDTO
        }

        coVerify(exactly = 1) { userService.findById(user.id) }
    }

    test("when there are user events, getUserEvents should return a list of user events") {
        val user = Instancio.of(userModel()).create()
        val userEvents = Instancio.ofList(userEventModel()).create()
        val userEventDTOs = userEvents.map { it.toUserEventDTO() }

        coEvery {
            userService.findEventsByUserId(user.id)
        } returns userEvents.asFlow()

        client.get().uri("/${user.id}/events").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk
            .expectBodyList(UserEventDTO::class.java).hasSize(userEvents.size).contains(
                *userEventDTOs.toTypedArray()
            )

        coVerify(exactly = 1) { userService.findEventsByUserId(user.id) }
    }

    test("addUser should add a user") {
        val user = Instancio.of(userModel()).create()
        val userDTO = user.toUserDTO()

        coEvery {
            userService.add(user)
        } returns user

        client.post().uri("/").accept(MediaType.APPLICATION_JSON).bodyValue(user).exchange()
            .expectStatus().isCreated.expectBody(UserDTO::class.java).value {
                it shouldBeEqual userDTO
            }

        coVerify(exactly = 1) { userService.add(user) }
    }

    test("addUserEvent should add a user event") {
        val userId = Instancio.create(UserId::class.java)
        val userEventCreationDTO = Instancio.create(UserEventCreationDTO::class.java)
        val userEvent = UserEvent.of(userEventCreationDTO.title, userEventCreationDTO.description, userId)
        val userEventDTO = userEvent.toUserEventDTO()

        coEvery {
            userService.addEvent(any())
        } returns userEvent

        client.post().uri("/${userId}/events").accept(MediaType.APPLICATION_JSON).bodyValue(userEventCreationDTO)
            .exchange()
            .expectStatus().isCreated.expectBody(UserEventDTO::class.java).value {
                it shouldBeEqual userEventDTO
            }
        coVerify(exactly = 1) { userService.addEvent(any()) }
    }

    test("updateUser should update a user") {
        val user = Instancio.create(User::class.java)
        val userCreationDTO = user.toUserCreationDTO()

        coEvery {
            userService.update(any())
        } returns user

        client.put().uri("/${user.id}").accept(MediaType.APPLICATION_JSON).bodyValue(userCreationDTO).exchange()
            .expectStatus().isOk.expectBody(UserCreationDTO::class.java).value {
                it shouldBeEqual userCreationDTO
            }
        coVerify(exactly = 1) { userService.update(any()) }
    }

    afterTest {
        clearMocks(userService)
    }

    test("deleteUser should delete a user") {
        val userId = Instancio.create(UserId::class.java)

        coEvery {
            userService.delete(userId)
        } returns Unit

        client.delete().uri("/${userId}").accept(MediaType.APPLICATION_JSON).exchange()
            .expectStatus().isOk
        coVerify(exactly = 1) { userService.delete(userId) }
    }

    test("when user event is not found, then getUserEvent should return not found") {
        val userId = Instancio.create(UserId::class.java)
        val eventId = Instancio.create(EventId::class.java)

        coEvery {
            userService.findEventById(eventId)
        } returns null

        client.get().uri("/${userId}/events/${eventId}").accept(MediaType.APPLICATION_JSON).exchange()
            .expectStatus().isNotFound

        coVerify(exactly = 1) { userService.findEventById(eventId) }
    }

    test("when user event is found, then getUserEvent should return the event") {
        val userId = Instancio.create(UserId::class.java)
        val userEvent = Instancio.of(userEventModel()).set(field(UserEvent::createdBy), userId).create()
        val userEventDTO = userEvent.toUserEventDTO()

        coEvery {
            userService.findEventById(userEvent.id)
        } returns userEvent

        client.get().uri("/${userId}/events/${userEvent.id}").accept(MediaType.APPLICATION_JSON).exchange()
            .expectStatus().isOk.expectBody(UserEventDTO::class.java).value {
                it shouldBeEqual userEventDTO
            }

        coVerify(exactly = 1) { userService.findEventById(userEvent.id) }
    }
})
