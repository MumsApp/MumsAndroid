package com.mumsapp.data.repository

import com.google.gson.JsonSyntaxException
import com.mumsapp.android.data.R
import com.mumsapp.domain.model.error.BaseServerError
import com.mumsapp.domain.model.error.ServerErrorException
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ExceptionDispatcher
import com.mumsapp.domain.utils.SerializationHelper
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

open class BaseRestRepository(val exceptionDispatcher: ExceptionDispatcher,
                              val serializationHelper: SerializationHelper,
                              val resourceRepository: ResourceRepository) {

    protected fun <T> requestWithErrorMapping(observable: Observable<Response<T>>): Observable<T> {
        return observable
                .map({
                    if(it.isSuccessful) {
                     it.body() as T
                } else {
                    if(exceptionDispatcher.isBadRequest(it.code())) {
                        val responseBody: ResponseBody? = it.errorBody()

                        throw createBadRequestException(responseBody)
                    }

                    throw throwServerError(resourceRepository.getString(R.string.error_unexpected))
                } })
    }

    private fun createBadRequestException(responseBody: ResponseBody?): Exception {
        if (responseBody != null) {
            try {
                val json = responseBody.string()
                val registerResponse = serializationHelper.fromJson(json, BaseServerError::class.java)

                return exceptionDispatcher.fromExceptionName(registerResponse.exceptionName)

            } catch (ignored: IOException) {
            } catch (ignored: JsonSyntaxException) {
            }

        }
        return throwServerError(resourceRepository.getString(R.string.error_server))
    }

    private fun throwServerError(message: String) = ServerErrorException(message)
}