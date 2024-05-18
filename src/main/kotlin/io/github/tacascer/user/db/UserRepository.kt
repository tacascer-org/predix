package io.github.tacascer.user.db

import io.github.tacascer.user.User

interface UserRepository {
    /**
     * Save a user.
     *
     * @param user The user to save. If the user is new, the id must be null
     * @return The saved user.
     */
    fun save(user: User): User

    /**
     * Find a user by id.
     *
     * @param id The id of the user to find.
     */
    fun findById(id: Long): User?
}
