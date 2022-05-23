package com.app.composetestingdemo.presentation.screens.registration

data class RegistrationScreenState(
    val isLoggingIn: Boolean = false,
    val email: String = "",
    val emailErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordErrorMessage: String? = null,
    val areTermsAccepted: Boolean = false,
    val termsAcceptanceErrorMessage: String? = null
)
