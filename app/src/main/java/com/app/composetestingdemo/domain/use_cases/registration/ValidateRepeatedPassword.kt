package com.app.composetestingdemo.domain.use_cases.registration

import javax.inject.Inject

class ValidateRepeatedPassword @Inject constructor() {
    operator fun invoke(): ValidationResult {
        return ValidationResult(
            isSuccessful = true
        )
    }
}