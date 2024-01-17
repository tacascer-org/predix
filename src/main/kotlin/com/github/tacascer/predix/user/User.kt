package com.github.tacascer.predix.user

import com.github.tacascer.predix.event.UserEvent
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.ReadOnlyProperty
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(@Id val id: Long, val name: String, @ReadOnlyProperty val events: List<UserEvent>, @Version val version: Long) {
    companion object {
        fun of(name: String): User {
            return User(0, name, listOf(), 0)
        }
    }
}
