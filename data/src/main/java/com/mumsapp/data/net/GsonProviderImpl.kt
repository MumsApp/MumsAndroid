package com.mumsapp.data.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mumsapp.data.net.adapter.ZonedDateTimeAdapter
import com.mumsapp.domain.net.GsonProvider
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class GsonProviderImpl : GsonProvider {

    private val zonedDateTimeAdapter: ZonedDateTimeAdapter

    private var gson: Gson? = null

    @Inject
    constructor(zonedDateTimeAdapter: ZonedDateTimeAdapter) {
        this.zonedDateTimeAdapter = zonedDateTimeAdapter
    }

    override fun provideGson(): Gson {
        if(gson == null) {
            gson = createGson()
        }

        return gson!!
    }

    private fun createGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(ZonedDateTime::class.java, zonedDateTimeAdapter)
                .create()
    }
}