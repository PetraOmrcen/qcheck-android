package com.example.myapplication

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest{

    @Test
    fun whenEmailIsValid(){
        val result = Validator.isEmailValid("tin.oroz@gmail.com")
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenEmailIsEmpty(){
        val result = Validator.isEmailValid("")
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun whenEmailIsInvalid(){
        val result = Validator.isEmailValid("tinoroz&gmail.com")
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun whenNameIsValid(){
        val result = Validator.isNameValid("Tin Oroz")
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenNameIsEmpty(){
        val result = Validator.isNameValid("")
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun whenNamrIsInvalid(){
        val result = Validator.isNameValid("tin")
        assertThat(result).isEqualTo(false)
    }

}