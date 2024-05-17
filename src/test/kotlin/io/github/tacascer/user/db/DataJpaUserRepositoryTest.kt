package io.github.tacascer.user.db

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe
import io.kotest.property.arbitrary.next
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan
@DataJpaTest
@Import(TestConfiguration::class)
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
})
