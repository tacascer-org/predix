package io.github.tacascer

import io.github.tacascer.user.UserService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.property.arbitrary.next
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestContainersConfiguration::class)
@SpringBootTest
class PredixApplicationKtTest(
    val userService: UserService,
) : FunSpec({
        test("can create new user") {
            // When
            val user = userService.create(userArb.next())

            // Then
            user.id!! shouldBeGreaterThan 0
        }

        test("can find user by id") {
            // Given
            val user = userService.create(userArb.next())

            // When
            val foundUser = userService.findById(user.id!!)

            // Then
            foundUser shouldBe user
        }

        test("can add prediction to user") {
            // Given
            val user = userService.create(userArb.next())
            val prediction = predictionArb.next()

            // When
            val updatedUser = userService.addPrediction(user.id!!, prediction)

            // Then
            updatedUser.predictions shouldContain prediction
        }
    })
