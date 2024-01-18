package com.github.tacascer.predix.user

import com.github.tacascer.predix.config.TestcontainersConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataR2dbcTest
@Import(TestcontainersConfig::class)
internal class UserRepositoryTest(private val userRepository: UserRepository) : FunSpec({
    test("User repository can save a user") {
        val user = User.of("tacascer")
        val savedUser = userRepository.save(user)
        savedUser.id shouldNotBe 0
        savedUser.name shouldBe "tacascer"
        savedUser.events shouldBe listOf()
        savedUser.version shouldNotBe 0
    }
})


