package io.github.tacascer.user

import io.github.tacascer.prediction.Prediction

interface UserService {
    /**
     * Create a new user.
     *
     * @param user The user to create. Must not have an id.
     * @return The created user with the id populated.
     */
    fun create(user: User): User

    fun addPrediction(
        userId: Long,
        prediction: Prediction,
    ): User

    fun findById(id: Long): User?
}
