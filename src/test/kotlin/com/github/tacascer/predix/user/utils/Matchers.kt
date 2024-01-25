package com.github.tacascer.predix.user.utils

import com.github.tacascer.predix.user.User
import com.github.tacascer.predix.user.UserEvent
import io.kotest.matchers.Matcher
import io.kotest.matchers.compose.all
import io.kotest.matchers.date.haveSameInstantAs
import io.kotest.matchers.equality.beEqualToIgnoringFields
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.reflection.havingProperty
import io.kotest.matchers.shouldBe

infix fun UserEvent.shouldBeEqual(other: UserEvent) = this shouldBe userEventMatcher(other)
infix fun User.shouldBeEqual(other: User) = this.shouldBeEqualToIgnoringFields(other, User::id, User::version)

infix fun Collection<UserEvent>.shouldBeEqual(other: Collection<UserEvent>) =
    this.shouldBeEqualToIgnoringFields(
        other,
        true,
        UserEvent::id,
        UserEvent::version,
        UserEvent::createdAt,
        UserEvent::lastModifiedDate
    )

fun userEventMatcher(userEvent: UserEvent) = Matcher.all(
    beEqualToIgnoringFields(
        userEvent,
        false,
        UserEvent::id,
        UserEvent::createdAt,
        UserEvent::lastModifiedDate,
        UserEvent::version
    ),
)
