package com.app.composetestingdemo.domain.use_cases.registration

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)
