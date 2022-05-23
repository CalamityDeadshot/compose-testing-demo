package com.app.composetestingdemo.domain.use_cases.registration

import android.util.Patterns
import com.app.composetestingdemo.data.di.IoDispatcher
import com.app.composetestingdemo.domain.use_cases.common.GetUsers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidateEmail @Inject constructor(
    private val getUsers: GetUsers,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String): ValidationResult {
        if (email.isEmpty()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Email cannot be empty"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Email is not valid"
            )
        }

        val userAlreadyPresent = withContext(ioDispatcher) {
            getUsers().first().any { it.email == email }
        }

        if (userAlreadyPresent) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "User with this email already exists"
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}