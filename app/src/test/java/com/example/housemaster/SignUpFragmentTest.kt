package com.example.housemaster

import com.google.common.truth.Truth
import org.junit.Assert.*

import org.junit.Test

class SignUpFragmentTest {

    @Test
    fun `empty first name return false`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validateFirstName("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid first name return true`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validateFirstName("Pubudu")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty last  name return false`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validateLastName("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid last  name return true`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validateLastName("Gunasekara")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty email return false`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validateEmail("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `empty password return false`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validatePassword("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `password less than 6 characters long return false`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validatePassword("12345")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid password return true`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validatePassword("123456")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty confirm password return false`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validateConPassword("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `confirm password less than 6 characters long return false`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validateConPassword("12345")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid confirm password return true`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validateConPassword("123456")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun ` Password And ConPassword are similar returns true`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validatePasswordAndConPassword("123456", "123456")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun ` Password And ConPassword are not similar returns true`() {
        var sf: SignUpFragment = SignUpFragment()
        val result = sf.validatePasswordAndConPassword("123456", "123451")

        Truth.assertThat(result).isFalse()
    }
}