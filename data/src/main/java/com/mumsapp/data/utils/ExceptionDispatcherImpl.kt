package com.mumsapp.data.utils

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.mumsapp.android.data.R
import com.mumsapp.domain.exceptions.LocalizedException
import com.mumsapp.domain.model.error.ServerErrorException
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ExceptionDispatcher
import javax.inject.Inject

class ExceptionDispatcherImpl : ExceptionDispatcher {

    private val resourceRepository: ResourceRepository

    @Inject
    constructor(resourceRepository: ResourceRepository) {
        this.resourceRepository = resourceRepository
    }

    override fun fromExceptionName(exceptionName: String): LocalizedException {

        return when (exceptionName) {
            "Exception" -> ServerErrorException(resourceRepository.getString(R.string.error_unexpected))

            else -> ServerErrorException(resourceRepository.getString(R.string.error_unexpected))
        }
    }

    override fun isBadRequest(throwable: Throwable): Boolean {
        return isBadRequest(getErrorCode(throwable))
    }

    override fun isBadRequest(responseCode: Int) = responseCode == 400

    override fun isUnAuthorized(throwable: Throwable): Boolean {
        return isUnAuthorized(getErrorCode(throwable))
    }

    override fun isUnAuthorized(responseCode: Int) = responseCode == 401

    private fun getErrorCode(throwable: Throwable): Int {
        return if (throwable is HttpException) {
            throwable.code()
        } else {
            -1
        }
    }
}