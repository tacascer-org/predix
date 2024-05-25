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

    override fun findById(id: Long): User? = userRepository.findById(id)

    override fun addPrediction(
        userId: Long,
        prediction: Prediction,
    ): User {
        val user =
            userRepository.findById(userId) ?: throw IllegalArgumentException("User with id $userId does not exist.")
        return userRepository.save(user.addPrediction(prediction))
    }
}
