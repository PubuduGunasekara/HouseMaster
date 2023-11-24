package com.example.housemaster

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class ResetPasswordFragmentTest{
    @Test
    fun `emtpy email returns false`(){
        var sf:ResetPasswordFragment = ResetPasswordFragment()
        val result = sf.validateEmail("")

        Truth.assertThat(result).isFalse()
    }

}