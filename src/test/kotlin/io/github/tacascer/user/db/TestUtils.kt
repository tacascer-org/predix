package io.github.tacascer.user.db

import io.github.tacascer.user.User
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string

val userArb = arbitrary {
    val name = Arb.string().next()
    User(name, mutableListOf())
}
