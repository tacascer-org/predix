package com.github.tacascer.predix.event

import java.time.LocalDateTime

typealias EventId = Long

interface Event {
    val id: EventId
    val title: String
    val description: String
    val createdAt: LocalDateTime
    val lastModifiedDate: LocalDateTime
    val version: Long
}