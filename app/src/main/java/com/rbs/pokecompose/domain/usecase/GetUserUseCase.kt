package com.rbs.pokecompose.domain.usecase

import com.rbs.pokecompose.domain.model.User
import com.rbs.pokecompose.domain.repository.UserRepository

class GetUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(username: String): User? = repository.getUserByUsername(username)
}