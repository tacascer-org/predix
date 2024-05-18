package io.github.tacascer.user.db

import io.github.tacascer.prediction.Prediction
import io.github.tacascer.prediction.db.PredictionValueTypeMapper
import io.github.tacascer.user.User
import org.springframework.stereotype.Component

@Component
class DataJpaUserRepository(
    private val userEntityRepository: UserEntityRepository,
    private val userEntityMapper: UserEntityMapper, private val predictionValueTypeMapper: PredictionValueTypeMapper
) : UserRepository {
    override fun create(user: User): User {
        val userEntity = userEntityMapper.toEntity(user)
        val savedUserEntity = userEntityRepository.save(userEntity)
        return userEntityMapper.toDomain(savedUserEntity)
    }

    override fun findById(id: Long): User? =
        userEntityRepository.findById(id)
            .map { userEntityMapper.toDomain(it) }
            .orElse(null)

    override fun addPrediction(userId: Long, prediction: Prediction): User {
        val userEntity = userEntityRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with id $userId not found") }
        userEntity.predictions.add(predictionValueTypeMapper.toEntity(prediction))
        return userEntityMapper.toDomain(userEntity)
    }
}
