package com.github.tacascer.predix.event

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

typealias EventId = Long

interface Event {
    val id: EventId
    val title: String
    val description: String
    val createdAt: OffsetDateTime
        get() = createdAt.truncatedTo(ChronoUnit.SECONDS)
    val lastModifiedDate: OffsetDateTime
        get() = lastModifiedDate.truncatedTo(ChronoUnit.SECONDS)
    val version: Long
}