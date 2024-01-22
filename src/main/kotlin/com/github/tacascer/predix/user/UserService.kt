package com.github.tacascer.predix.user

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(val userRepository: UserRepository, val userEventRepository: UserEventRepository) {
    @Transactional(readOnly = true)
    suspend fun findById(id: UserId): User? {
        val user = userRepository.findById(id)
        user?.let {
            it.events.addAll(userEventRepository.findAllByCreatedByOrderByCreatedAtDesc(it.id).toList())
        }
        return user
    }
}