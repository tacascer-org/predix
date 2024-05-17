package io.github.tacascer.user.config

import io.github.tacascer.user.DomainUserService
import io.github.tacascer.user.UserService
import io.github.tacascer.user.db.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserConfiguration {
    @Bean
    fun userService(userRepository: UserRepository): UserService {
        return DomainUserService(userRepository)
    }
}
