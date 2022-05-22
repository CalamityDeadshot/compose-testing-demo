package com.app.composetestingdemo.domain.use_cases.registration


import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateEmailTest {

    lateinit var validateEmail: ValidateEmail

    @Before
    fun setUp() {
        validateEmail = ValidateEmail()
    }

    @Test
    fun `empty email returns empty error`() {
        val nonValidEmail = "eqwrafd@ds"
        val validationResult = validateEmail(nonValidEmail)
        assertThat(validationResult.isSuccessful).isFalse()
        assertThat(validationResult.errorMessage).isEqualTo("Email cannot be empty")
    }

    @Test
    fun `non-valid email returns non-valid error`() {
        val nonValidEmail = "eqwrafd@ds"
        val validationResult = validateEmail(nonValidEmail)
        assertThat(validationResult.isSuccessful).isFalse()
        assertThat(validationResult.errorMessage).isEqualTo("Email is not valid")
    }

    @Test
    fun `valid email returns success`() {
        val validEmail = "test@test.com"
        val validationResult = validateEmail(validEmail)
        assertThat(validationResult.isSuccessful).isTrue()
        assertThat(validationResult.errorMessage).isEqualTo(null)
    }
}