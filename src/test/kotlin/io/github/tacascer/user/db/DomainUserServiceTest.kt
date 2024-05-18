package io.github.tacascer.user.db

import io.github.tacascer.user.DomainUserService
import io.github.tacascer.user.User
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldNotBe
import io.kotest.property.arbitrary.next
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DomainUserServiceTest : FunSpec({
    val userRepository = mockk<UserRepository>()
    val userService = DomainUserService(userRepository)

    test("when user is saved, then user is returned with a populated id") {
        // Given
        val user = userArb.next()

        every {
            userRepository.save(user)
        } returns User(user.name, user.predictions, 1L)

        // When
        val savedUser = userService.create(user)

        // Then
        savedUser.id shouldNotBe null
        verify {
            userRepository.save(user)
        }
    }

    test("when prediction is added to user, then user is returned with the prediction added") {
        // Given
        val user = userArb.next()
        val prediction = predictionArb.next()

        every {
            userRepository.findById(1L)
        } returns user

        // When
        userService.addPrediction(1L, prediction)

        // Then
        user.predictions.shouldNotBeEmpty()
        verify {
            userRepository.findById(1L)
        }
    }
})
