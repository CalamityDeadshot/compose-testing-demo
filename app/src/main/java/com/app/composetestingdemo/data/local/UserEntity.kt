package com.app.composetestingdemo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: Long? = null,
    val email: String,
    val password: String
)
