package com.mumsapp.domain.utils

interface ValidationHelper {

    fun checkIsNotEmpty(reference: String?): Boolean

    fun checkIsNotEmpty(reference: Collection<*>?): Boolean

    fun checkIsNotEmpty(obj: Any?): Boolean

    fun checkEmailValid(email: String?): Boolean

    fun checkPasswordValid(password: String?): Boolean

    fun checkNameValid(name: String?): Boolean

    fun areFieldsTheSame(first: String?, second: String?): Boolean

    fun checkIsCorrectPrice(price: String?): Boolean

    fun checkIsCorrectPrice(price: Float?): Boolean
}