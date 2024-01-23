package com.github.tacascer.predix.user

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {
    @GetMapping("/{id}")
    suspend fun getUser(@PathVariable id: UserId): User? {
        return userService.findById(id)
    }

    @GetMapping("/{id}/events")
    fun getUserEvents(@PathVariable id: UserId): Flow<UserEvent> {
        return userService.findEventsByUserId(id)
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addUser(@RequestBody user: UserCreationDTO): User {
        return userService.add(User.of(user.name))
    }

    @PostMapping("/{id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addUserEvent(@PathVariable id: UserId, @RequestBody userEvent: UserEventCreationDTO): UserEvent {
        return userService.addEvent(UserEvent.of(userEvent.title, userEvent.description, id))
    }
}

data class UserCreationDTO(val name: String)
data class UserEventCreationDTO(val title: String, val description: String)
