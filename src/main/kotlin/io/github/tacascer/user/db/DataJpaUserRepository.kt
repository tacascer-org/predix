package io.github.tacascer.user.db

import io.github.tacascer.user.User

class DataJpaUserRepository(
    private val userEntityRepository: UserEntityRepository,
    private val userEntityMapper: UserEntityMapper
) : UserRepository {
    override fun save(user: User): User {
        val userEntity = userEntityMapper.toEntity(user)
        val savedUserEntity = userEntityRepository.save(userEntity)
        return userEntityMapper.toDto(savedUserEntity)
    }
}