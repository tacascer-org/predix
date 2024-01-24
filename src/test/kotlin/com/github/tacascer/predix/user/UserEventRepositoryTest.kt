package com.github.tacascer.predix.user

import com.github.tacascer.predix.config.TestcontainersConfig
import com.github.tacascer.predix.user.utils.shouldBeEqual
import io.kotest.core.spec.style.FunSpec
import org.instancio.Instancio
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataR2dbcTest
@Import(TestcontainersConfig::class)
internal class UserEventRepositoryTest(
    private val userEventRepository: UserEventRepository,
    private val userRepository: UserRepository,
) : FunSpec({
    beforeSpec {
        userRepository.save(User.of(Instancio.create(String::class.java)))
    }

    afterTest {
        userEventRepository.deleteAll()
    }

    test("User event repository can save a user event") {
        val userEvent = UserEvent.of(Instancio.create(String::class.java), Instancio.create(String::class.java), 1)
        val savedUserEvent = userEventRepository.save(userEvent)
        savedUserEvent shouldBeEqual userEvent
    }
})