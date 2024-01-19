package com.github.tacascer.predix.user

import com.github.tacascer.predix.config.TestcontainersConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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
    private val transactionalOperator: TransactionalOperator
) : FunSpec({
    beforeTest {
        transactionalOperator.executeAndAwait {
            userEventRepository.deleteAll()
        }
    }

    test("User event repository can save a user event") {
        val userEvent = UserEvent.of("test", "test", 1)
        transactionalOperator.executeAndAwait {
            val savedUserEvent = userEventRepository.save(userEvent)
            savedUserEvent.id shouldNotBe 0
            savedUserEvent.createdBy shouldBeEqual 1
            savedUserEvent.description shouldBe "test"
            savedUserEvent.version shouldNotBe 0
        }
    }

})