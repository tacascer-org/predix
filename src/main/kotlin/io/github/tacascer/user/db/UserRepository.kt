package io.github.tacascer.user.db

import io.github.tacascer.user.User

interface UserRepository {
    /**
     * Save a user.
     *
     * @param user The user to save. This user should not have an id.
     * @return The user with the id populated.
     */
    fun save(user: User): User

    /**
     * Find a user by id.
     *
     * @param id The id of the user to find.
     */
    fun findById(id: Long): User?
}
