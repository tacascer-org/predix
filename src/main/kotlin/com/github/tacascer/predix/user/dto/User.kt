package com.github.tacascer.predix.user.dto

import com.github.tacascer.predix.event.EventId
import com.github.tacascer.predix.user.User
import com.github.tacascer.predix.user.UserEvent
import com.github.tacascer.predix.user.UserId

data class UserCreationDTO(val name: String) {
    fun toUser(): User = User.of(name)
    fun toUser(id: UserId): User = User.of(id, name)
}

data class UserDTO(val id: UserId, val name: String) {
    fun toUser(): User = User.of(id, name)
}

data class UserEventCreationDTO(val title: String, val description: String)

data class UserEventDTO(val id: EventId, val title: String, val description: String, val createdBy: UserId)

fun User.toUserDTO(): UserDTO {
    return UserDTO(id, name)
}

fun User.toUserCreationDTO(): UserCreationDTO {
    return UserCreationDTO(name)
}

fun UserEvent.toUserEventDTO(): UserEventDTO {
    return UserEventDTO(id, title, description, createdBy)
}

