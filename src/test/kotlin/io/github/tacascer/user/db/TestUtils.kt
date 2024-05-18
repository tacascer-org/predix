package io.github.tacascer.user.db

import io.github.tacascer.prediction.Prediction
import io.github.tacascer.user.User
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*

val userArb = arbitrary {
    val name = Arb.string().next()
    User(name, mutableSetOf())
}

val predictionArb = arbitrary {
    val outcome = Arb.boolean().next()
    Prediction(outcome)
}
