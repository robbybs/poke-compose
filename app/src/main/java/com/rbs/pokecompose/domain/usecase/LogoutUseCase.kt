package com.rbs.pokecompose.domain.usecase

import com.rbs.pokecompose.domain.repository.UserRepository

class LogoutUseCase(private val repository: UserRepository) {
    suspend operator fun invoke() = repository.logout()
}