package io.github.tacascer.user.db

import io.github.tacascer.TestContainersConfiguration
import io.github.tacascer.predictionArb
import io.github.tacascer.userArb
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.arbitrary.next
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = ["io.github.tacascer.user.db", "io.github.tacascer.prediction.db"])
@DataJpaTest
@Import(TestContainersConfiguration::class)
class DataJpaUserRepositoryTest(
    val userRepository: DataJpaUserRepository,
) : FunSpec({
        test("given a user, when user is saved, then user is returned with a populated id") {
            // Given
            val user = userArb.next()

            // When
            val savedUser = userRepository.save(user)

            // Then
            savedUser.id shouldNotBe null
        }

        test("given a user id, when user is searched, if user exists, then user is returned") {
            // Given
            val user = userArb.next()
            val savedUser = userRepository.save(user)

            // When
            val foundUser = userRepository.findById(savedUser.id!!)

            // Then
            foundUser shouldNotBe null
        }

        test("given a user id, when user is searched, if user does not exist, then null is returned") {
            // Given
            val id = 1L

            // When
            val foundUser = userRepository.findById(id)

            // Then
            foundUser shouldBe null
        }

        test("given an existing user, when user has a prediction added, then user is returned with the prediction") {
            // Given
            val user = userArb.next()
            val savedUser = userRepository.save(user)
            val prediction = predictionArb.next()

            // When
            val updatedUser = userRepository.save(savedUser.addPrediction(prediction))

            // Then
            updatedUser.predictions.shouldNotBeEmpty()
        }
    })
