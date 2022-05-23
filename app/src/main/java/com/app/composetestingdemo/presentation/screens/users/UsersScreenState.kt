package com.app.composetestingdemo.presentation.screens.users

import com.app.composetestingdemo.domain.models.User

data class UsersScreenState(
    val usersList: List<User> = emptyList(),
    val selectedUserIds: List<Long> = emptyList()
)
