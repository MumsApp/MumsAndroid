package com.mumsapp.data.utils

import com.mumsapp.android.data.R
import com.mumsapp.domain.exceptions.LocalizedException
import com.mumsapp.domain.model.error.ServerErrorException
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ExceptionDispatcher
import retrofit2.HttpException
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
        if (throwable is HttpException) {
            val code = throwable.code()

            return isBadRequest(code)
        }

        return false
    }

    override fun isBadRequest(responseCode: Int) = responseCode == 400

    override fun isUnAuthorized(responseCode: Int) = responseCode == 401
}