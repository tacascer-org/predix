package com.github.tacascer.predix.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(val userRepository: UserRepository, val userEventRepository: UserEventRepository) {
    @Transactional(readOnly = true)
    suspend fun findById(id: UserId): User? {
        return userRepository.findById(id)?.apply {
            events.addAll(userEventRepository.findAllByCreatedByOrderByCreatedAtDesc(id).toList())
        }
    }

    @Transactional(readOnly = true)
    fun findEventsByUserId(id: UserId): Flow<UserEvent> {
        return userEventRepository.findAllByCreatedByOrderByCreatedAtDesc(id)
    }

    @Transactional
    suspend fun add(user: User): User {
        return userRepository.save(user)
    }

    @Transactional
    suspend fun addEvent(userEvent: UserEvent): UserEvent {
        return userEventRepository.save(userEvent)
    }

    @Transactional
    suspend fun update(user: User): User {
        TODO("Not yet implemented")
    }
}