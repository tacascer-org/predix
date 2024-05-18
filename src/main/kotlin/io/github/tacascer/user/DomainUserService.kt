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

    override fun addPrediction(user: User, prediction: Prediction): User {
        requireNotNull(userRepository.findById(user.id!!)) { "User with id ${user.id} does not exist" }
        user.addPrediction(prediction)
        return userRepository.save(user)
    }
}
