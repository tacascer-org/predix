package com.github.tacascer.predix.user

import com.github.tacascer.predix.config.TestcontainersConfig
import com.github.tacascer.predix.instancio.field
import io.kotest.core.spec.style.FunSpec
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

    test("UserService can find a user by ID") {
        val user = Instancio.of(userModel()).create()
        val savedUser = userRepository.save(user)
        val userEvents = Instancio.ofList(userEventModel()).set(field(UserEvent::createdBy), savedUser.id).create()
        val savedUserEvents = userEventRepository.saveAll(userEvents).toList()

        val foundUser = userService.findById(savedUser.id)

        foundUser!! shouldBe savedUser
        foundUser.events.shouldBeEqualToIgnoringFields(
            savedUserEvents,
            UserEvent::createdAt,
            UserEvent::lastModifiedDate
        )
    }
})
