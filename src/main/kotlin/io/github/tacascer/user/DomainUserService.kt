package io.github.tacascer.user

import io.github.tacascer.prediction.Prediction
import io.github.tacascer.user.db.UserRepository
import jakarta.transaction.Transactional

@Transactional
class DomainUserService(
    private val userRepository: UserRepository,
) : UserService {
    override fun create(user: User): User {
        return userRepository.create(user)
    }

    override fun addPrediction(userId: Long, prediction: Prediction): User {
        return userRepository.addPrediction(userId, prediction)
    }
}
