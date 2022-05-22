package com.app.composetestingdemo.domain.use_cases.registration

import com.app.composetestingdemo.common.Constants
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test

class ValidatePasswordTest {

    lateinit var validatePassword: ValidatePassword

    @Before
    fun setUp() {
        validatePassword = ValidatePassword()
    }

    @Test
    fun `correct password returns success`() {
        val password = buildString {
            for (i in 0.. Constants.PASSWORD_MIN_LENGTH + 1) {
                append("p1")
            }
        }
        val result = validatePassword(password)
        assertThat(result.isSuccessful).isTrue()
        assertThat(result.errorMessage).isNull()
    }

    @Test
    fun `short password returns error`() {
        val password = buildString {
            for (i in 0 until Constants.PASSWORD_MIN_LENGTH - 1) {
                append("p")
            }
        }
        val result = validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo("Password must be at least ${Constants.PASSWORD_MIN_LENGTH} characters long")
    }

    @Test
    fun `letters-only password returns error`() {
        val password = buildString {
            for (i in 0.. Constants.PASSWORD_MIN_LENGTH + 1) {
                append("p")
            }
        }
        val result = validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo("Password must contain at least one digit")
    }

    @Test
    fun `numbers-only password returns error`() {
        val password = buildString {
            for (i in 0.. Constants.PASSWORD_MIN_LENGTH + 1) {
                append("1")
            }
        }
        val result = validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo("Password must contain at least one letter")
    }
}