package com.github.tacascer.predix.user

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(val userRepository: UserRepository, val userEventRepository: UserEventRepository) {
    @Transactional(readOnly = true)
    suspend fun findById(id: UserId): User? {
        return userRepository.findById(id)
    }

    @Transactional(readOnly = true)
    fun findEventsByUserId(id: UserId): Flow<UserEvent> {
        return userEventRepository.findAllByCreatedByOrderByCreatedAtDesc(id)
    }

    suspend fun add(user: User): User {
        return userRepository.save(user)
    }

    suspend fun addEvent(userEvent: UserEvent): UserEvent {
        return userEventRepository.save(userEvent)
    }

    suspend fun update(user: User): User {
        return userRepository.save(user)
    }

    suspend fun delete(id: UserId) {
        userEventRepository.deleteAllByCreatedBy(id)
        return userRepository.deleteById(id)
    }
}