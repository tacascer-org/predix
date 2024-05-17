package io.github.tacascer.user

import io.github.tacascer.user.db.UserRepository

class DomainUserService(
    private val userRepository: UserRepository,
) : UserService {
    override fun save(user: User): User {
        return userRepository.save(user)
    }
}
