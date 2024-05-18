package io.github.tacascer.user.db

import io.github.tacascer.user.DomainUserService
import io.github.tacascer.user.User
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.arbitrary.next
import io.mockk.*

class DomainUserServiceTest : FunSpec({
    val userRepository = mockk<UserRepository>()
    val userService = DomainUserService(userRepository)

    afterTest {
        clearAllMocks()
        confirmVerified()
        checkUnnecessaryStub()
    }

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
    }

    test("when prediction is added to user, then user is returned with the prediction added") {
        // Given
        val user = existingUserArb.next()
        val prediction = predictionArb.next()

        val expected = User(user.name, user.predictions + prediction, user.id)

        every {
            userRepository.findById(user.id!!)
        } returns user

        every {
            userRepository.save(user)
        } returns expected

        // When
        val result = userService.addPrediction(user, prediction)

        // Then
        result shouldBe expected
    }
})
