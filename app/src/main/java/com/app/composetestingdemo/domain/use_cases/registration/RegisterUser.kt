package com.app.composetestingdemo.domain.use_cases.registration

import com.app.composetestingdemo.data.di.IoDispatcher
import com.app.composetestingdemo.data.local.UserEntity
import com.app.composetestingdemo.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val usersRepository: UsersRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String, password: String) = withContext(ioDispatcher) {
        usersRepository.addUser(
            UserEntity(
                email = email,
                password = password
            )
        )
    }
}