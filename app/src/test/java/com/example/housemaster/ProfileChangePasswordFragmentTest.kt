package com.example.housemaster

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class ProfileChangePasswordFragmentTest {
    @Test
    fun `empty current password return false`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validateCurrentPassword("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid current password return true`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validateCurrentPassword("123456")

        Truth.assertThat(result).isTrue()
    }


    @Test
    fun `empty new password return false`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validateNewPassword("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `new password less than 6 characters long return false`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validateNewPassword("12345")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid new password return true`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validateNewPassword("123456")

        Truth.assertThat(result).isTrue()
    }


    @Test
    fun `empty new confirm password return false`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validateConPassword("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `new confirm password less than 6 characters long return false`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validateConPassword("12345")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid new confirm password return true`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validateConPassword("123456")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `new Password And ConPassword are same return true`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validatePasswordAndConPassword("123456", "123456")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `new Password And ConPassword are not same return false`() {
        var sf: ProfileChangePasswordFragment = ProfileChangePasswordFragment()
        val result = sf.validatePasswordAndConPassword("123456", "123459")

        Truth.assertThat(result).isFalse()
    }

}