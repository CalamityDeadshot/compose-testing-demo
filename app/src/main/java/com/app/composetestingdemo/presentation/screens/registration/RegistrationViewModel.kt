package com.app.composetestingdemo.presentation.screens.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.composetestingdemo.data.di.IoDispatcher
import com.app.composetestingdemo.domain.use_cases.registration.ValidateEmail
import com.app.composetestingdemo.domain.use_cases.registration.ValidatePassword
import com.app.composetestingdemo.domain.use_cases.registration.ValidateRepeatedPassword
import com.app.composetestingdemo.domain.use_cases.registration.ValidateTermsAcceptance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateRepeatedPassword: ValidateRepeatedPassword,
    private val validateTermsAcceptance: ValidateTermsAcceptance
) : ViewModel() {

    var state by mutableStateOf(RegistrationScreenState())
        private set

    private val validationChannel = Channel<ValidationEvent>()
    val validationEventsFlow = validationChannel.receiveAsFlow()

    fun onEmailChanged(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password)
    }

    fun onRepeatedPasswordChanged(repeatedPassword: String) {
        state = state.copy(repeatedPassword = repeatedPassword)
    }

    fun onTermsAcceptanceChanged(isAccepted: Boolean) {
        state = state.copy(areTermsAccepted = isAccepted)
    }

    fun onSubmit() = viewModelScope.launch {
        val emailResult = validateEmail(state.email)
        val passwordResult = validatePassword(state.password)
        val repeatedPasswordResult = validateRepeatedPassword(state.password, state.repeatedPassword)
        val termsResult = validateTermsAcceptance(state.areTermsAccepted)

        state = state.copy(
            emailErrorMessage = emailResult.errorMessage,
            passwordErrorMessage = passwordResult.errorMessage,
            repeatedPasswordErrorMessage = repeatedPasswordResult.errorMessage,
            termsAcceptanceErrorMessage = termsResult.errorMessage
        )

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult
        ).any { !it.isSuccessful }

        if (!hasError) {
            validationChannel.send(ValidationEvent.Success)
        }

    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }

}