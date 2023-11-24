package com.example.housemaster

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class AddCardsAndAccountsFragmentTest {
    @Test
    fun `empty name on card return false`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateNameOnCard("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid name on card return true`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateNameOnCard("Pubudu Gunasekara")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty card number return false`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateCardNumber("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid card number return true`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateCardNumber("1234123412341234")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty expMonth return false`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateExpMonth("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid exp month return true`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateExpMonth("December")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty exp year return false`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateExpYear("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid exp year return true`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateExpYear("2024")

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty cvv return false`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateCVV("")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `valid cvv return true`() {
        var sf: AddCardsAndAccountsFragment = AddCardsAndAccountsFragment()
        val result = sf.validateCVV("123")

        Truth.assertThat(result).isTrue()
    }
}