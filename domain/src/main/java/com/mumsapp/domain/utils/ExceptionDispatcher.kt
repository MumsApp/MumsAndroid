package com.mumsapp.domain.utils

import com.mumsapp.domain.exceptions.LocalizedException

interface ExceptionDispatcher {

    fun fromExceptionName(exceptionName: String): LocalizedException

    fun isBadRequest(throwable: Throwable): Boolean

    fun isBadRequest(responseCode: Int): Boolean

    fun isUnAuthorized(responseCode: Int): Boolean
}