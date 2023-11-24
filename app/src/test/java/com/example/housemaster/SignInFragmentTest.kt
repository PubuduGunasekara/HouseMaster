package com.example.housemaster

import com.example.housemaster.databinding.FragmentSigninBinding
import org.junit.Assert.*
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SignInFragmentTest{
    @Test
    fun `emtpy email returns false`(){
        var sf:SignInFragment = SignInFragment()
        val result = sf.validateEmail("")

        assertThat(result).isFalse()
    }
    @Test
    fun `emtpy password returns false`(){
        var sf:SignInFragment = SignInFragment()
        val result = sf.validatePassword("")

        assertThat(result).isFalse()
    }

    @Test
    fun `valid password returns true`(){
        var sf:SignInFragment = SignInFragment()
        val result = sf.validatePassword("password123")

        assertThat(result).isTrue()
    }


}