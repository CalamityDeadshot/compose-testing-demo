package com.app.composetestingdemo.presentation.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel()
) {

    val state = registrationViewModel.state

    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(true) {
        registrationViewModel.validationEventsFlow.collect {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = "Success!"
            )
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(
            snackbarHostState = snackbarHostState
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.email,
                placeholder = {
                    Text(text = "Email address")
                },
                onValueChange = registrationViewModel::onEmailChanged,
                isError = state.emailErrorMessage != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            if (state.emailErrorMessage != null)
                Text(text = state.emailErrorMessage, color = MaterialTheme.colors.error)

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.password,
                placeholder = {
                    Text(text = "Password")
                },
                onValueChange = registrationViewModel::onPasswordChanged,
                isError = state.passwordErrorMessage != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            if (state.passwordErrorMessage != null)
                Text(text = state.passwordErrorMessage, color = MaterialTheme.colors.error)

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.repeatedPassword,
                placeholder = {
                    Text(text = "Repeat password")
                },
                onValueChange = registrationViewModel::onRepeatedPasswordChanged,
                isError = state.repeatedPasswordErrorMessage != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            if (state.repeatedPasswordErrorMessage != null)
                Text(text = state.repeatedPasswordErrorMessage, color = MaterialTheme.colors.error)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = state.areTermsAccepted,
                    onCheckedChange = registrationViewModel::onTermsAcceptanceChanged
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Accept terms"
                )
            }
            if (state.termsAcceptanceErrorMessage != null)
                Text(text = state.termsAcceptanceErrorMessage, color = MaterialTheme.colors.error)

            Button(
                onClick = registrationViewModel::onSubmit,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Submit")
            }
        }
    }
}