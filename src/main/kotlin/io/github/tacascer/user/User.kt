package io.github.tacascer.user

import io.github.tacascer.prediction.Prediction

/**
 * User domain object.
 * Preconditions:
 * - If the user is new, the [id] must be null.
 * - If the user has [predictions], the [id] must not be null.
 *
 * @property id the user's id. If null, the user is new.
 * @property name the user's name
 * @property predictions the user's predictions
 *
 */
data class User(
    val name: String,
    val predictions: Set<Prediction> = emptySet(),
    val id: Long? = null,
) {
    init {
        validateState()
    }

    fun addPrediction(prediction: Prediction): User {
        validateState()
        return copy(predictions = predictions + prediction)
    }

    private fun validateState() {
        if (id == null) {
            check(predictions.isEmpty()) { "New user cannot have predictions" }
        }

        if (predictions.isNotEmpty()) {
            checkNotNull(id) { "User with predictions must have an id" }
        }
    }
}
