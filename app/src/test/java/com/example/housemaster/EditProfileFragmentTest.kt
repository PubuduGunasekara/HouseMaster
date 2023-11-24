package com.example.housemaster

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class EditProfileFragmentTest {
    @Test
    fun `empty first name return false`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateFirstName("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid first name return true`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateFirstName("Pubudu")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty last name return false`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateLastName("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid last name return true`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateLastName("Gunasekara")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty email return false`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateEmail("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `empty phone number return false`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validatePhone("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `empty street address return false`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateStreetAddress("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid street address return true`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateStreetAddress("203 Albert Street")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty suite apt number return false`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateSuiteAptNumber("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid suite apt number return true`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateSuiteAptNumber("101")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty city return false`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateCity("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid city return true`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateCity("Waterloo")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty postal code return false`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateCity("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid postal code return true`() {
        var sf: EditProfileFragment = EditProfileFragment()
        val result = sf.validateCity("N2L 3T4")

        Truth.assertThat(result).isTrue()
    }


}