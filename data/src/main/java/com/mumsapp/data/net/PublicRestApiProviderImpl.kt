package com.mumsapp.data.net

import com.google.gson.Gson
import com.mumsapp.domain.net.PublicRestApi
import com.mumsapp.domain.net.PublicRestApiProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import okhttp3.Interceptor
import okhttp3.Request
import javax.inject.Inject

class PublicRestApiProviderImpl(val tokenService: TokenPersistenceService, endpoint: String, gson: Gson) :
        BaseRetrofitApiProvider<PublicRestApi>(endpoint, apiTimeout, PublicRestApi::class.java, gson),
        PublicRestApiProvider {

    companion object {
        private val apiTimeout: Long = 20
    }


    override fun provideRestApi(): PublicRestApi = getApi()

    override fun createNetworkInterceptor(): Interceptor? {
        return Interceptor {
            if(tokenService.getToken() != null) {
                val request = it.request()

                val authToken = tokenService.getToken()?.getAuthHeader()

                val newRequest = request.newBuilder()
                        .addHeader("Authorization", authToken!!)
                        .build()
                it.proceed(newRequest)
            } else {
                it.proceed(it.request())
            }
        }
    }
}