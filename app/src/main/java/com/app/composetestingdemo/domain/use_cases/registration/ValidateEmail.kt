package com.app.composetestingdemo.domain.use_cases.registration

import android.util.Patterns
import javax.inject.Inject

class ValidateEmail @Inject constructor() {
    operator fun invoke(email: String): ValidationResult =
        if (email.isEmpty()) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Email cannot be empty"
            )
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Email is not valid"
            )
        } else ValidationResult(
            isSuccessful = true
        )
}