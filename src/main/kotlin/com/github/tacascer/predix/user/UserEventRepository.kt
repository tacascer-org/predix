package com.github.tacascer.predix.user

import com.github.tacascer.predix.event.EventId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserEventRepository : CoroutineCrudRepository<UserEvent, EventId> {}