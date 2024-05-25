package io.github.tacascer.prediction.db

import jakarta.persistence.Embeddable
import org.hibernate.proxy.HibernateProxy
import java.util.Objects

@Embeddable
class PredictionValueType(
    var outcome: Boolean,
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as PredictionValueType

        return outcome == other.outcome
    }

    final override fun hashCode(): Int = Objects.hash(outcome)
}
