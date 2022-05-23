package com.app.composetestingdemo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(
        user: UserEntity
    )

    @Query("SELECT * FROM UserEntity")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("DELETE FROM UserEntity")
    suspend fun delete()
}