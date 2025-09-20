package com.rbs.pokecompose.data.mapper

import com.rbs.pokecompose.data.local.entity.UserEntity
import com.rbs.pokecompose.domain.model.User

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    username = username
)