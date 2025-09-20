package com.rbs.pokecompose.domain.usecase

import com.rbs.pokecompose.domain.repository.UserRepository

class RegisterUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(name: String, username: String, password: String) {
        val existingUser = repository.getUserByUsername(username)
        if (existingUser != null) {
            throw IllegalArgumentException("Username already exists")
        }
        return repository.register(name, username, password)
    }
}