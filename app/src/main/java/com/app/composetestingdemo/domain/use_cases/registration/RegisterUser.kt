package com.app.composetestingdemo.domain.use_cases.registration

import com.app.composetestingdemo.data.local.UserEntity
import com.app.composetestingdemo.domain.repository.UsersRepository
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        usersRepository.addUser(
            UserEntity(
                email = email,
                password = password
            )
        )
    }
}