package com.app.composetestingdemo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.composetestingdemo.domain.models.User

@Entity
data class UserEntity(
    @PrimaryKey val id: Long? = null,
    val email: String,
    val password: String
) {
    fun toUser() = User(email, id!!)
}
