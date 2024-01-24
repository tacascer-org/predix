package com.github.tacascer.predix.user.dto

import com.github.tacascer.predix.event.EventId
import com.github.tacascer.predix.user.User
import com.github.tacascer.predix.user.UserEvent
import com.github.tacascer.predix.user.UserId

data class UserCreationDTO(val name: String) {
    fun toUser(): User {
        return User.of(name)
    }
}

data class UserEventCreationDTO(val title: String, val description: String)

data class UserEventDTO(val id: EventId, val title: String, val description: String, val createdBy: UserId) {
    fun toUserEvent(): UserEvent {
        return UserEvent.of(title, description, createdBy)
    }
}

fun UserEvent.toUserEventDTO(): UserEventDTO {
    return UserEventDTO(id, title, description, createdBy)
}