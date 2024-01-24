package com.github.tacascer.predix.user

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

    @GetMapping("/{id}/events")
    fun getUserEvents(@PathVariable id: UserId): Flow<UserEventDTO> {
        return userService.findEventsByUserId(id).map { it.toUserEventDTO() }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addUser(@RequestBody user: UserCreationDTO): UserDTO {
        return userService.add(user.toUser()).toUserDTO()
    }

    @PostMapping("/{id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addUserEvent(@PathVariable id: UserId, @RequestBody userEvent: UserEventCreationDTO): UserEventDTO {
        return userService.addEvent(UserEvent.of(userEvent.title, userEvent.description, id)).toUserEventDTO()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateUser(@RequestBody userDTO: UserDTO): UserDTO {
        return userService.update(userDTO.toUser()).toUserDTO()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun deleteUser(@PathVariable id: UserId) {
        userService.delete(id)
    }
}
