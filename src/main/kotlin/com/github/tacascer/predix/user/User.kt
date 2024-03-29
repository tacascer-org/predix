package com.github.tacascer.predix.user

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table


typealias UserId = Long

@Table("users")
data class User(
    @Id val id: UserId,
    val name: String,
    @Version val version: Long
) {
    @Transient
    val events: MutableCollection<UserEvent> = mutableListOf()

    companion object {
        fun of(name: String): User {
            return User(0, name, version = 0)
        }

        fun of(id: UserId, name: String): User {
            return User(id, name, version = 0)
        }
    }
}
