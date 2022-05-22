package com.app.composetestingdemo.domain.use_cases.registration

import com.app.composetestingdemo.common.Constants
import javax.inject.Inject

class ValidatePassword @Inject constructor() {
    operator fun invoke(password: String): ValidationResult {
        if (password.length < Constants.PASSWORD_MIN_LENGTH) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password must be at least ${Constants.PASSWORD_MIN_LENGTH} characters long"
            )
        }

        val containsLetters = password.any { it.isLetter() }
        val containsDigits = password.any { it.isDigit() }
        if (!containsLetters) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password must contain at least one letter"
            )
        }
        if (!containsDigits) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password must contain at least one digit"
            )
        }
        return ValidationResult(
            isSuccessful = true,
        )
    }
}