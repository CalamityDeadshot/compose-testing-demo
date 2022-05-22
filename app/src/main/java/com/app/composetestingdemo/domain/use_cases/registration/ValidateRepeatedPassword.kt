package com.app.composetestingdemo.domain.use_cases.registration

import javax.inject.Inject

class ValidateRepeatedPassword @Inject constructor() {
    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Passwords do not match"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}