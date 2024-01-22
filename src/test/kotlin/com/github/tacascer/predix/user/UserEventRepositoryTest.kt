package com.github.tacascer.predix.user

import com.github.tacascer.predix.config.TestcontainersConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import org.instancio.Instancio
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@ActiveProfiles("test")
@DataR2dbcTest
@Import(TestcontainersConfig::class)
internal class UserEventRepositoryTest(
    private val userEventRepository: UserEventRepository,
    private val userRepository: UserRepository,
    private val transactionalOperator: TransactionalOperator
) : FunSpec({
    beforeSpec {
        transactionalOperator.executeAndAwait {
            userRepository.save(User.of(Instancio.create(String::class.java)))
        }
    }

    afterTest {
        transactionalOperator.executeAndAwait {
            userEventRepository.deleteAll()
        }
    }

    test("User event repository can save a user event") {
        val userEvent = UserEvent.of(Instancio.create(String::class.java), Instancio.create(String::class.java), 1)
        val savedUserEvent = transactionalOperator.executeAndAwait {
            userEventRepository.save(userEvent)
        }
        savedUserEvent.shouldBeEqualToIgnoringFields(userEvent, UserEvent::id, UserEvent::version)
    }

})