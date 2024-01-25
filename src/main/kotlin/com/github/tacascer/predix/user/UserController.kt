package com.github.tacascer.predix.user

import com.github.tacascer.predix.event.EventId
import com.github.tacascer.predix.user.dto.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {
    @GetMapping("/{id}")
    suspend fun getUser(@PathVariable id: UserId): ResponseEntity<UserDTO> {
        return userService.findById(id)?.let {
            ResponseEntity.ok(it.toUserDTO())
        } ?: ResponseEntity.notFound().build()
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addUser(@RequestBody user: UserCreationDTO): UserDTO {
        return userService.add(user.toUser()).toUserDTO()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateUser(@PathVariable id: UserId, @RequestBody userCreationDTO: UserCreationDTO): UserCreationDTO {
        return userService.update(userCreationDTO.toUser(id)).toUserCreationDTO()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun deleteUser(@PathVariable id: UserId) {
        userService.delete(id)
    }

    @GetMapping("/{id}/events")
    fun getUserEvents(@PathVariable id: UserId): Flow<UserEventDTO> {
        return userService.findEventsByUserId(id).map { it.toUserEventDTO() }
    }

    @PostMapping("/{id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addUserEvent(@PathVariable id: UserId, @RequestBody userEvent: UserEventCreationDTO): UserEventDTO {
        return userService.addEvent(UserEvent.of(userEvent.title, userEvent.description, id)).toUserEventDTO()
    }

    @GetMapping("/{id}/events/{eventId}")
    suspend fun getUserEvent(@PathVariable id: UserId, @PathVariable eventId: EventId): ResponseEntity<UserEventDTO> {
        return userService.findEventById(eventId)?.let {
            ResponseEntity.ok(it.toUserEventDTO())
        } ?: ResponseEntity.notFound().build()
    }
}

