package com.github.tacascer.predix.event

import java.time.OffsetDateTime

typealias EventId = Long

interface Event {
    val id: EventId
    val title: String
    val description: String
    val createdAt: OffsetDateTime
    val lastModifiedDate: OffsetDateTime
    val version: Long
}