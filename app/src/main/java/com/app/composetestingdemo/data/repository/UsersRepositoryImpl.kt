package com.app.composetestingdemo.data.repository

import com.app.composetestingdemo.data.local.UserEntity
import com.app.composetestingdemo.data.local.UsersDatabase
import com.app.composetestingdemo.domain.models.User
import com.app.composetestingdemo.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl @Inject constructor(
    db: UsersDatabase
): UsersRepository {

    private val dao = db.dao

    override fun getUsers(): Flow<List<User>> = dao.getUsers().map {
        it.map { it.toUser() }
    }

    override fun getUserEntities(): Flow<List<UserEntity>> = dao.getUsers()

    override suspend fun addUser(user: UserEntity) = dao.insertUser(user)
}