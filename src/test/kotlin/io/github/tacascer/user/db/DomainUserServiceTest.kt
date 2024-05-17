package io.github.tacascer.user.db

import io.github.tacascer.user.DomainUserService
import io.github.tacascer.user.User
import io.kotest.core.spec.style.FunSpec
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
        val savedUser = userService.save(user)

        // Then
        savedUser.id shouldNotBe null
        verify {
            userRepository.save(user)
        }
    }
})
