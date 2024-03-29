package com.github.tacascer.predix.user

import com.github.tacascer.predix.event.Event
import com.github.tacascer.predix.event.EventId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

@Table("user_events")
data class UserEvent(
    @Id override val id: EventId,
    override val title: String,
    override val description: String,
    val createdBy: UserId,
    @CreatedDate override val createdAt: OffsetDateTime = OffsetDateTime.now(),
    @LastModifiedDate override val lastModifiedDate: OffsetDateTime = OffsetDateTime.now(),
    @Version override val version: Long
) : Event {
    companion object {
        fun of(title: String, description: String, createdBy: UserId): UserEvent {
            return UserEvent(0, title, description, createdBy, version = 0)
        }
    }
}
