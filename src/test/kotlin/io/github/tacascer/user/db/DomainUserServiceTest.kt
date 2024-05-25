package io.github.tacascer.user.db

import io.github.tacascer.existingUserArb
import io.github.tacascer.predictionArb
import io.github.tacascer.user.DomainUserService
import io.github.tacascer.user.User
import io.github.tacascer.userArb
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.arbitrary.next
import io.mockk.checkUnnecessaryStub
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk

class DomainUserServiceTest : FunSpec({
    val userRepository = mockk<UserRepository>()
    val userService = DomainUserService(userRepository)

    afterTest {
        clearAllMocks()
        confirmVerified()
        checkUnnecessaryStub()
    }

    context("create user") {
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
    }

    context("find user by id") {
        test("given a user id, when user is searched, if user exists, then user is returned") {
            // Given
            val user = existingUserArb.next()

            every {
                userRepository.findById(user.id!!)
            } returns user

            // When
            val foundUser = userService.findById(user.id!!)

            // Then
            foundUser shouldBe user
        }

        test("given a user id, when user is searched, if user does not exist, then null is returned") {
            // Given
            val id = 1L

            every {
                userRepository.findById(id)
            } returns null

            // When
            val foundUser = userService.findById(id)

            // Then
            foundUser shouldBe null
        }
    }

    context("add prediction") {
        test("given a non-existing user, when prediction is added to user, then exception is thrown") {
            // Given
            val user = existingUserArb.next()
            val prediction = predictionArb.next()

            every {
                userRepository.findById(user.id!!)
            } returns null

            // When
            val exception =
                shouldThrow<IllegalArgumentException> {
                    userService.addPrediction(user.id!!, prediction)
                }

            // Then
            exception.message shouldBe "User with id ${user.id} does not exist."
        }

        test("given an existing user, when prediction is added to user, then user is returned with the prediction added") {
            // Given
            val user = existingUserArb.next()
            val prediction = predictionArb.next()

            val expected = User(user.name, user.predictions + prediction, user.id)

            every {
                userRepository.findById(user.id!!)
            } returns user

            every {
                userRepository.save(any())
            } returns expected

            // When
            val result = userService.addPrediction(user.id!!, prediction)

            // Then
            result shouldBe expected
        }
    }
})
