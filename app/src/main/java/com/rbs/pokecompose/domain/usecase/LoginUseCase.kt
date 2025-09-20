package com.rbs.pokecompose.domain.usecase

import com.rbs.pokecompose.domain.model.User
import com.rbs.pokecompose.domain.repository.UserRepository

class LoginUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(username: String, password: String): User? =
        repository.login(username, password)
}