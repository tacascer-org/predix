package com.github.tacascer.predix.user

import com.github.tacascer.predix.event.Event
import com.github.tacascer.predix.event.EventId
import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("user_events")
data class UserEvent(
    @Id override val id: EventId,
    override val title: String,
    override val description: String,
    @CreatedBy val createdBy: UserId,
    @CreatedDate override val createdAt: LocalDateTime,
    @LastModifiedDate override val lastModifiedDate: LocalDateTime,
    @Version override val version: Long
) : Event {
    companion object {
        fun of(title: String, description: String, createdBy: UserId): UserEvent {
            return UserEvent(0, title, description, createdBy, LocalDateTime.now(), LocalDateTime.now(), 0)
        }
    }
}
