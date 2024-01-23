package com.github.tacascer.predix.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {
    @GetMapping("/{id}")
    suspend fun getUser(@PathVariable id: UserId): User? {
        return userService.findById(id)
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addUser(@RequestBody user: User): User {
        return userService.add(user)
    }

    @PostMapping("/{id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addUserEvent(@RequestBody userEvent: UserEvent): UserEvent {
        return userService.addEvent(userEvent)
    }
}