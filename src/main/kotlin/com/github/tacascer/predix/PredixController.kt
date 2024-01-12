package com.github.tacascer.predix

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PredixController {
    @GetMapping("/")
    suspend fun getPredix(): String {
        return "Hello world"
    }

    @GetMapping("/users")
    fun getUsers(): Flow<User> {
        return flowOf(User("tacascer"), User("tacascer2"))
    }
}