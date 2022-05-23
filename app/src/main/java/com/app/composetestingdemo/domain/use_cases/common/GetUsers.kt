package com.app.composetestingdemo.domain.use_cases.common

import com.app.composetestingdemo.domain.repository.UsersRepository
import javax.inject.Inject

class GetUsers @Inject constructor(
    private val usersRepository: UsersRepository
) {
    operator fun invoke() = usersRepository.getUsers()
}