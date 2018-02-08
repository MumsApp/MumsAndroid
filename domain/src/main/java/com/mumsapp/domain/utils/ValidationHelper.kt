package com.mumsapp.domain.utils

import io.reactivex.annotations.Nullable

interface ValidationHelper {

    fun checkIsNotEmpty(reference: String?): Boolean

    fun checkIsNotEmpty(reference: Collection<*>?): Boolean

    fun checkEmailValid(email: String?): Boolean

    fun checkPasswordValid(password: String?): Boolean

    fun checkNameValid(name: String?): Boolean

    fun areFieldsTheSame(first: String?, second: String?): Boolean
}