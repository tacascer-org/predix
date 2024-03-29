package com.github.tacascer.predix.user

import com.github.tacascer.predix.config.TestcontainersConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.equals.shouldBeEqual
import org.instancio.Instancio
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@ActiveProfiles("test")
@DataR2dbcTest
@Import(TestcontainersConfig::class)
internal class UserRepositoryTest(
    private val userRepository: UserRepository,
    private val transactionalOperator: TransactionalOperator
) : FunSpec({
    beforeSpec {
        transactionalOperator.executeAndAwait {
            userRepository.deleteAll()
        }
    }

    afterTest {
        transactionalOperator.executeAndAwait {
            userRepository.deleteAll()
        }
    }

    test("User repository can save a user") {
        val user = User.of(Instancio.create(String::class.java))
        val savedUser = transactionalOperator.executeAndAwait {
            userRepository.save(user)
        }
        savedUser.shouldBeEqualToIgnoringFields(user, User::id, User::version)
    }

    test("User repository can find a user by id") {
        val user = User.of(Instancio.create(String::class.java))
        val (savedUser, foundUser) = transactionalOperator.executeAndAwait {
            val savedUser = userRepository.save(user)
            val foundUser = userRepository.findById(savedUser.id)
            Pair(savedUser, foundUser)
        }
        foundUser!! shouldBeEqual savedUser
    }

    test("User repository can find a user by name") {
        val user = User.of(Instancio.create(String::class.java))
        val (savedUser, foundUser) = transactionalOperator.executeAndAwait {
            val savedUser = userRepository.save(user)
            val foundUser = userRepository.findByName(savedUser.name)
            Pair(savedUser, foundUser)
        }
        foundUser!! shouldBeEqual savedUser
    }
})


