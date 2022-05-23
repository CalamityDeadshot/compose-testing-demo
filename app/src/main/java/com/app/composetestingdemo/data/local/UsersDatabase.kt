package com.app.composetestingdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class UsersDatabase: RoomDatabase() {
    abstract val dao: UsersDao

    companion object {
        const val NAME = "USERS_DATABASE"
    }
}