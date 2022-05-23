package com.app.composetestingdemo.domain.repository

import com.app.composetestingdemo.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(): Flow<List<UserEntity>>
    suspend fun addUser(
        user: UserEntity
    )
}