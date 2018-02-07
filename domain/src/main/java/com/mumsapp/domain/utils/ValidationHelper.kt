package com.mumsapp.domain.utils

import io.reactivex.annotations.Nullable

interface ValidationHelper {

    fun checkIsEmpty(reference: String?): Boolean

    fun checkIsEmpty(reference: Collection<*>?): Boolean

    fun checkEmailValid(email: String?): Boolean

    fun checkPasswordValid(password: String?): Boolean

    fun checkNameValid(firstName: String?): Boolean

    fun areFieldsTheSame(first: String?, second: String?): Boolean
}