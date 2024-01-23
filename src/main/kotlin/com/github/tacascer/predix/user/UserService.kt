package com.github.tacascer.predix.user

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

    @Transactional
    suspend fun add(user: User): User {
        return userRepository.save(user)
    }

    @Transactional
    suspend fun addEvent(userEvent: UserEvent): UserEvent {
        return userEventRepository.save(userEvent)
    }
}