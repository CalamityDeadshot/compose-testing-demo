package com.app.composetestingdemo.presentation.screens.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.composetestingdemo.common.TestTags
import com.app.composetestingdemo.presentation.navigation.AppScreen
import kotlinx.coroutines.flow.collect

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val state = registrationViewModel.state

    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(true) {
        registrationViewModel.validationEventsFlow.collect {
            navHostController.navigate(AppScreen.UsersList.route) {
                popUpTo(AppScreen.Register.route) {
                    inclusive = true
                }
            }
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
                .padding(32.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.EMAIL_FIELD_TEST_TAG),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.PASSWORD_FIELD_TEST_TAG),
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

            AnimatedVisibility(visible = !state.isLoggingIn) {
                Column {
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
                        Text(
                            text = state.repeatedPasswordErrorMessage,
                            color = MaterialTheme.colors.error
                        )

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
                        Text(
                            text = state.termsAcceptanceErrorMessage,
                            color = MaterialTheme.colors.error
                        )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    modifier = Modifier
                        .testTag(TestTags.LOGIN_TOGGLE_TEST_TAG),
                    onClick = registrationViewModel::onLoginToggle
                ) {
                    Text(text = if (state.isLoggingIn) "Register" else "Login")
                }

                Button(
                    modifier = Modifier
                        .testTag(TestTags.SUBMIT_TEST_TAG),
                    onClick = registrationViewModel::onSubmit
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}