package com.github.tacascer.predix.user

import com.github.tacascer.predix.user.dto.UserCreationDTO
import com.github.tacascer.predix.user.dto.UserEventCreationDTO
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
        return userService.add(user.toUser())
    }

    @PostMapping("/{id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addUserEvent(@PathVariable id: UserId, @RequestBody userEvent: UserEventCreationDTO): UserEvent {
        return userService.addEvent(UserEvent.of(userEvent.title, userEvent.description, id))
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateUser(@RequestBody user: User): User {
        return userService.update(user)
    }
}
