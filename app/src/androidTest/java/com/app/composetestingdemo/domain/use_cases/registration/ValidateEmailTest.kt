package com.app.composetestingdemo.domain.use_cases.registration


import com.app.composetestingdemo.data.di.DataModule
import com.app.composetestingdemo.data.di.DispatchersModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(DispatchersModule::class, DataModule::class)
class ValidateEmailTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var validateEmail: ValidateEmail

    @Inject
    lateinit var registerUser: RegisterUser

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun emptyEmail_returnsEmptyError() = runBlockingTest {
        val nonValidEmail = ""
        val validationResult = validateEmail(nonValidEmail, false)
        assertThat(validationResult.isSuccessful).isFalse()
        assertThat(validationResult.errorMessage).isEqualTo("Email cannot be empty")
    }

    @Test
    fun nonValidEmail_returnsNonValidError() = runBlockingTest {
        val nonValidEmail = "eqwrafd@ds"
        val validationResult = validateEmail(nonValidEmail, false)
        assertThat(validationResult.isSuccessful).isFalse()
        assertThat(validationResult.errorMessage).isEqualTo("Email is not valid")
    }

    @Test
    fun emailExists_returnsExistingError() = runBlockingTest {
        val email = "test@test.test"
        registerUser(email, "sadasdqewe12")
        val result = validateEmail(email, false)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo("User with this email already exists")
    }

    @Test
    fun login_emailDoesNotExist_returnsNotExistingError() = runBlockingTest {
        val email = "test@test.test"
        val result = validateEmail(email, true)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo("This email does not exist")
    }

    @Test
    fun validEmail_returnsSuccess() = runBlockingTest {
        val validEmail = "test@test.com"
        val validationResult = validateEmail(validEmail, false)
        assertThat(validationResult.isSuccessful).isTrue()
        assertThat(validationResult.errorMessage).isEqualTo(null)
    }
}