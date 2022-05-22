package com.app.composetestingdemo.domain.use_cases.registration

import javax.inject.Inject

class ValidateTermsAcceptance @Inject constructor() {
    operator fun invoke(isAccepted: Boolean): ValidationResult {
        if (!isAccepted) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Terms must be accepted"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}