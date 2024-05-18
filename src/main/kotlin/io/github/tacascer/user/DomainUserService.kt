package io.github.tacascer.user

import io.github.tacascer.prediction.Prediction
import io.github.tacascer.user.db.UserRepository
import jakarta.transaction.Transactional

@Transactional
class DomainUserService(
    private val userRepository: UserRepository,
) : UserService {
    override fun create(user: User): User {
        return userRepository.save(user)
    }

    override fun addPrediction(userId: Long, prediction: Prediction) {
        val user = userRepository.findById(userId) ?: throw IllegalArgumentException("User not found")
        user.addPrediction(prediction)
    }
}
