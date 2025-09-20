package com.rbs.pokecompose.domain.repository

import com.rbs.pokecompose.domain.model.User
import com.rbs.pokecompose.domain.model.UserSession

interface UserRepository {
    suspend fun login(username: String, password: String): User?
    suspend fun register(name: String, username: String, password: String)
    suspend fun getUserByUsername(username: String): User?
    suspend fun getUserData(): UserSession
    suspend fun logout()
}