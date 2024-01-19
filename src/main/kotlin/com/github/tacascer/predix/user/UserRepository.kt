package com.github.tacascer.predix.user

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<User, UserId> {
    suspend fun findByName(name: String): User?
}