package io.github.tacascer.user.db

import io.github.tacascer.prediction.Prediction
import io.github.tacascer.user.User

interface UserRepository {
    /**
     * Create a user.
     *
     * @param user The user to save. User id must be null.
     * @return The saved user with an id.
     */
    fun create(user: User): User

    /**
     * Find a user by id.
     *
     * @param id The id of the user to find.
     */
    fun findById(id: Long): User?

    /**
     * Add a prediction to a user.
     *
     * @param userId The id of the user to add the prediction to.
     * @param prediction The prediction to add.
     * @throws IllegalArgumentException If the user with the given id does not exist.
     * @return The updated user.
     */
    fun addPrediction(userId: Long, prediction: Prediction): User
}
