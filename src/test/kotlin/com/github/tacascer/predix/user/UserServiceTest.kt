package com.github.tacascer.predix.user

import com.github.tacascer.predix.config.TestcontainersConfig
import com.github.tacascer.predix.instancio.field
import com.github.tacascer.predix.user.utils.shouldBeSameAs
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

    test("findById returns a user without events") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val userEvents = Instancio.ofList(userEventModel()).set(field(UserEvent::createdBy), savedUser.id).create()
        userEventRepository.saveAll(userEvents).toList()

        val foundUser = userService.findById(savedUser.id)

        foundUser!! shouldBe savedUser
        foundUser.events.shouldBeEmpty()
    }

    test("given User that doesn't exist, when UserService finds by ID, then it returns null") {
        val foundUser = userService.findById(1)

        foundUser shouldBe null
    }

    test("UserService can find events of user by ID") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val userEvents = Instancio.ofList(userEventModel()).set(field(UserEvent::createdBy), savedUser.id).create()
        val savedUserEvents = userEventRepository.saveAll(userEvents).toList()

        val foundUserEvents = userService.findEventsByUserId(savedUser.id).toList()

        foundUserEvents shouldBeSameAs savedUserEvents
    }

    test("UserService can add a user") {
        val user = Instancio.of(userModel()).create()

        val savedUser = userService.add(user)

        savedUser.shouldBeEqualToIgnoringFields(user, User::id, User::version)
        savedUser.events.shouldBeEmpty()
    }

    test("UserService can add a user event") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val userEvent = Instancio.of(userEventModel()).set(field(UserEvent::createdBy), savedUser.id).create()

        val savedUserEvent = userService.addEvent(userEvent)

        savedUserEvent shouldBeSameAs userEvent
    }
})
