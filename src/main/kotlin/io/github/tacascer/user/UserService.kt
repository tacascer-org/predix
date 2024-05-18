package io.github.tacascer.user

fun interface UserService {
    fun save(user: User): User
}
