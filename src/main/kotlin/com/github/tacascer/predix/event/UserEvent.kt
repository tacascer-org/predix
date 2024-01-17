package com.github.tacascer.predix.event

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class UserEvent(
    @Id override val id: Long,
    override val title: String,
    override val description: String,
    @CreatedBy override val createdBy: String,
    @CreatedDate override val createdAt: LocalDateTime,
    @LastModifiedDate override val lastModifiedDate: LocalDateTime,
    @Version override val version: Long
) : Event
