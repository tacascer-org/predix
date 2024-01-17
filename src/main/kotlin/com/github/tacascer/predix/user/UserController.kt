package com.github.tacascer.predix.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {
    @GetMapping
    fun getUsers(): Flow<User> {
        return flowOf(User.of("tacascer"), User.of("tacascer2"))
    }
}