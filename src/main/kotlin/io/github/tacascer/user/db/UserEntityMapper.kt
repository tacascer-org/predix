package io.github.tacascer.user.db

import io.github.tacascer.user.User
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface UserEntityMapper {
    fun toEntity(user: User): UserEntity

    fun toDomain(userEntity: UserEntity): User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun partialUpdate(
        user: User,
        @MappingTarget userEntity: UserEntity,
    ): UserEntity
}
