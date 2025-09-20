package com.rbs.pokecompose.domain.usecase

import com.rbs.pokecompose.domain.model.UserSession
import com.rbs.pokecompose.domain.repository.UserRepository

class GetStatusUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): UserSession = repository.getUserData()
}