package com.mumsapp.data.net

import com.google.gson.Gson
import com.mumsapp.domain.net.PublicRestApi
import com.mumsapp.domain.net.PublicRestApiProvider
import javax.inject.Inject

class PublicRestApiProviderImpl(endpoint: String, gson: Gson) :
        BaseRetrofitApiProvider<PublicRestApi>(endpoint, apiTimeout, PublicRestApi::class.java, gson),
        PublicRestApiProvider {

    companion object {
        private val apiTimeout: Long = 20
    }


    override fun provideRestApi(): PublicRestApi = getApi()
}