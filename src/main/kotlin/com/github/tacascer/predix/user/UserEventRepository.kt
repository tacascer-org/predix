package com.github.tacascer.predix.user

import com.github.tacascer.predix.event.EventId
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserEventRepository : CoroutineCrudRepository<UserEvent, EventId> {
    fun findAllByCreatedByOrderByCreatedAtDesc(createdBy: UserId): Flow<UserEvent>

    @Modifying
    suspend fun deleteAllByCreatedBy(createdBy: UserId)
}