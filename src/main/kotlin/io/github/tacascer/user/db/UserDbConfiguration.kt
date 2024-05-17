package io.github.tacascer.user.db

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserDbConfiguration {
    @Bean
    fun userRepository(userEntityRepository: UserEntityRepository, userEntityMapper: UserEntityMapper): UserRepository {
        return DataJpaUserRepository(userEntityRepository, userEntityMapper)
    }
}
