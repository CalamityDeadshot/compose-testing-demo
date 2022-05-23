package com.app.composetestingdemo.domain.use_cases.registration

import com.app.composetestingdemo.data.di.IoDispatcher
import com.app.composetestingdemo.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidateLoginCredentials @Inject constructor(
    private val usersRepository: UsersRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String, password: String): ValidationResult =
        withContext(ioDispatcher) {
            val areCredentialsCorrect =
                usersRepository.getUserEntities().first()
                    .any { it.email == email && it.password == password }

            return@withContext if (areCredentialsCorrect) {
                ValidationResult(
                    isSuccessful = true
                )
            } else ValidationResult(
                isSuccessful = false,
                errorMessage = "Password is not correct"
            )
        }
}