package com.github.tacascer.predix.event

import java.time.LocalDateTime

interface Event {
    val id: Long
    val title: String
    val description: String
    val createdBy: String
    val createdAt: LocalDateTime
    val lastModifiedDate: LocalDateTime
    val version: Long
}