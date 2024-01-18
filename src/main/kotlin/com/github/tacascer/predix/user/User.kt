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
    val events: List<UserEvent> = listOf()

    companion object {
        fun of(name: String): User {
            return User(0, name, 0)
        }
    }
}
