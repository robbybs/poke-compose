package com.rbs.pokecompose.data.repository

import com.rbs.pokecompose.data.local.UserPreferences
import com.rbs.pokecompose.data.local.dao.UserDao
import com.rbs.pokecompose.data.local.entity.UserEntity
import com.rbs.pokecompose.data.mapper.toDomain
import com.rbs.pokecompose.domain.model.User
import com.rbs.pokecompose.domain.model.UserSession
import com.rbs.pokecompose.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class UserRepoImpl(
    private val dao: UserDao,
    private val preferences: UserPreferences
) : UserRepository {
    override suspend fun login(
        username: String,
        password: String
    ): User? = withContext(Dispatchers.IO) {
        val userEntity = dao.getUser(username, password)
        userEntity?.let {
            preferences.setLogin(true, it.username)
            it.toDomain()
        }
    }

    override suspend fun register(
        name: String,
        username: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        val data = UserEntity(
            name = name,
            username = username,
            password = password
        )
        dao.insertUser(data)
    }

    override suspend fun getUserByUsername(username: String): User? = withContext(Dispatchers.IO) {
        dao.getUserByUsername(username)?.toDomain()
    }

    override suspend fun getUserData(): UserSession = withContext(Dispatchers.IO) {
        val isLoggedIn = preferences.isLogin.first()
        val username = preferences.username.first()
        UserSession(isLoggedIn, username)
    }

    override suspend fun logout() = withContext(Dispatchers.IO) {
        preferences.clearSession()
    }
}