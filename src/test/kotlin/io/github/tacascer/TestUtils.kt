package io.github.tacascer

import io.github.tacascer.prediction.Prediction
import io.github.tacascer.user.User
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*

/**
 * Returns a new user with a random name and an empty set of predictions
 */
val userArb = arbitrary {
    val name = Arb.string().next()
    User(name, mutableSetOf())
}

/**
 * Returns an existing user with a random name and an empty set of predictions
 */
val existingUserArb = userArb.map { it.copy(id = Arb.positiveLong().next()) }

/**
 * Returns a new user with a random name and a set of predictions
 */
val predictionArb = arbitrary {
    val outcome = Arb.boolean().next()
    Prediction(outcome)
}
