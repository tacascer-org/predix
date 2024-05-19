package io.github.tacascer.user.db

import io.github.tacascer.user.User
import org.mapstruct.*

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface UserEntityMapper {

    fun toEntity(user: User): UserEntity

    fun toDomain(userEntity: UserEntity): User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun partialUpdate(user: User, @MappingTarget userEntity: UserEntity): UserEntity
}
