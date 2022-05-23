package com.app.composetestingdemo.domain.repository

import com.app.composetestingdemo.data.local.UserEntity
import com.app.composetestingdemo.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(): Flow<List<User>>
    fun getUserEntities(): Flow<List<UserEntity>>
    suspend fun addUser(
        user: UserEntity
    )
}