package com.github.tacascer.predix.user

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import org.instancio.Instancio

class UserServiceTest : FunSpec({
    val userRepository = mockk<UserRepository>()
    val userEventRepository = mockk<UserEventRepository>()

    val userService = UserService(userRepository, userEventRepository)

    test("UserService should be able to find a user by id") {
        val user = Instancio.of(userModel()).create()
        val userEvents = Instancio.ofList(userEventModel()).create()

        coEvery {
            userRepository.findById(user.id)
        } returns user

        coEvery {
            userEventRepository.findTop10ByCreatedByOrderByCreatedAtDesc(user.id)
        } returns userEvents.asFlow()

        val foundUser = userService.findById(user.id)

        foundUser!! shouldBe user
        foundUser.events shouldBe userEvents

        coVerify(exactly = 1) { userRepository.findById(user.id) }
        coVerify(exactly = 1) { userEventRepository.findTop10ByCreatedByOrderByCreatedAtDesc(user.id) }
    }
})
