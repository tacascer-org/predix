package io.github.tacascer.user.db

import io.github.tacascer.prediction.PredictionValueType
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "users")
class UserEntity(
    @NotBlank
    @Column(name = "name")
    val name: String,

    @ElementCollection
    @CollectionTable(name = "users_predictions", joinColumns = [JoinColumn(name = "user_id")])
    val predictions: MutableSet<PredictionValueType> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
) {
    @Version
    @Column(name = "version")
    var version: Int = 0
        protected set
}
