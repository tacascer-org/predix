package com.github.tacascer.predix.user

import com.github.tacascer.predix.config.TestcontainersConfig
import com.github.tacascer.predix.instancio.field
import com.github.tacascer.predix.user.utils.shouldBeEqual
import com.github.tacascer.predix.user.utils.userEventModel
import com.github.tacascer.predix.user.utils.userModel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import org.instancio.Instancio
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import

@DataR2dbcTest
@Import(TestcontainersConfig::class)
class UserServiceTest(
    private val userRepository: UserRepository,
    private val userEventRepository: UserEventRepository
) : FunSpec({
    val userService = UserService(userRepository, userEventRepository)

    afterTest {
        userEventRepository.deleteAll()
        userRepository.deleteAll()
    }

    test("given a user that doesn't exist, findById returns null") {
        val foundUser = userService.findById(1)

        foundUser shouldBe null
    }

    test("given an existing user, findById returns a user without events") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val userEvents = Instancio.ofList(userEventModel()).set(field(UserEvent::createdBy), savedUser.id).create()
        userEventRepository.saveAll(userEvents).toList()

        val foundUser = userService.findById(savedUser.id)

        foundUser!! shouldBe savedUser
        foundUser.events.shouldBeEmpty()
    }

    test("given a user with no events, findEventsByUserId returns an empty list") {
        val user = Instancio.of(userModel()).create()

        val savedUser = userService.add(user)

        savedUser.shouldBeEqualToIgnoringFields(user, User::id, User::version)
        savedUser.events.shouldBeEmpty()
    }

    test("given a user with events, findEventsByUserId returns a list of user events") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val userEvents = Instancio.ofList(userEventModel()).set(field(UserEvent::createdBy), savedUser.id).create()
        val savedUserEvents = userEventRepository.saveAll(userEvents).toList()

        val foundUserEvents = userService.findEventsByUserId(savedUser.id).toList()

        foundUserEvents shouldBeEqual savedUserEvents
    }

    test("given a user, addEvent returns the added user event") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val userEvent = Instancio.of(userEventModel()).set(field(UserEvent::createdBy), savedUser.id).create()

        val savedUserEvent = userService.addEvent(userEvent)

        savedUserEvent shouldBeEqual userEvent
    }

    test("given a user with non-zero version number, update returns the user with updated version") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val modifiedUser =
            Instancio.of(userModel()).set(field(User::version), savedUser.version).set(field(User::id), savedUser.id)
                .create()

        val updatedUser = userService.update(modifiedUser)

        updatedUser.shouldBeEqualToIgnoringFields(modifiedUser, User::version)
    }

    test("given a user without events, delete deletes the user") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)

        userService.delete(savedUser.id)

        userRepository.findById(savedUser.id) shouldBe null
    }

    test("given a user with events, delete deletes the user and its events") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val userEvents = Instancio.ofList(userEventModel()).set(field(UserEvent::createdBy), savedUser.id).create()
        userEventRepository.saveAll(userEvents).toList()

        userService.delete(savedUser.id)

        userRepository.findById(savedUser.id) shouldBe null
        userEventRepository.findAllByCreatedByOrderByCreatedAtDesc(savedUser.id).toList().shouldBeEmpty()
    }

    test("given a user with an event, findEventByUserIdAndEventId returns the event") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val userEvent = Instancio.of(userEventModel()).set(field(UserEvent::createdBy), savedUser.id).create()
        val savedUserEvent = userEventRepository.save(userEvent)

        val foundUserEvent = userService.findEventById(savedUserEvent.id)

        foundUserEvent!! shouldBeEqual savedUserEvent
    }
})
