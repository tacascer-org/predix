package com.github.tacascer.predix.user

import com.github.tacascer.predix.instancio.field
import org.instancio.Instancio
import org.instancio.Model

fun userModel(): Model<User> =
    Instancio.of(User::class.java).set(field(User::events), mutableListOf<UserEvent>()).set(field(User::version), 0)
        .set(field(User::id), 0).toModel()

fun userEventModel(): Model<UserEvent> =
    Instancio.of(UserEvent::class.java).set(field(UserEvent::id), 0).set(field(UserEvent::version), 0)
        .toModel()
