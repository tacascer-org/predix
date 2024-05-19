package io.github.tacascer.user.db

import io.github.tacascer.prediction.db.PredictionValueType
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "users")
class UserEntity(
    @NotBlank
    @Column(name = "name")
    var name: String,

    @ElementCollection
    @CollectionTable(name = "users_predictions", joinColumns = [JoinColumn(name = "user_id")])
    var predictions: MutableSet<PredictionValueType> = mutableSetOf(),

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
