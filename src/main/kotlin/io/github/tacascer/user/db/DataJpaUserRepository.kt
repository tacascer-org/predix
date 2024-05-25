package io.github.tacascer.user.db

import io.github.tacascer.user.User
import org.springframework.stereotype.Component

@Component
class DataJpaUserRepository(
    private val userEntityRepository: UserEntityRepository,
    private val userEntityMapper: UserEntityMapper,
) : UserRepository {
    override fun save(user: User): User {
        val userEntity = userEntityMapper.toEntity(user)
        val savedUserEntity = userEntityRepository.save(userEntity)
        return userEntityMapper.toDomain(savedUserEntity)
    }

    override fun findById(id: Long): User? =
        userEntityRepository.findById(id)
            .map { userEntityMapper.toDomain(it) }
            .orElse(null)
}
