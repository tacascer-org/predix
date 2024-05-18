package io.github.tacascer.user

import io.github.tacascer.prediction.Prediction

/**
 * User domain object.
 * @property id the user's id. If null, the user is new.
 * @property name the user's name
 * @property predictions the user's predictions
 */
data class User(
    val name: String,
    val predictions: Set<Prediction>,
    val id: Long? = null,
) {
    fun addPrediction(prediction: Prediction) = this.copy(predictions = predictions + prediction)
}
