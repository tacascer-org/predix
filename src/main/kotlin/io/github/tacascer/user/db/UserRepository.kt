package io.github.tacascer.user.db

import io.github.tacascer.user.User

interface UserRepository {
    /**
     * Create a user.
     *
     * @param user The user to save. If the user is new, the id should be null.
     * @return The saved user. If the user is new, the id should be set.
     */
    fun save(user: User): User

    /**
     * Find a user by id.
     *
     * @param id The id of the user to find.
     */
    fun findById(id: Long): User?
}
