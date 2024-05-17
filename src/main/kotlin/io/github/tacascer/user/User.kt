package io.github.tacascer.user

import io.github.tacascer.prediction.Prediction

/**
 * User domain object.
 * @property id the user's id. If null, the user is new.
 * @property name the user's name
 * @property predictions the user's predictions
 */
class User(
    val name: String,
    val predictions: MutableList<Prediction>,
    val id: Long? = null,
) {
    fun addPrediction(prediction: Prediction) {
        predictions.add(prediction)
    }
}
