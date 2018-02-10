package com.mumsapp.data.utils

import com.google.gson.Gson
import com.mumsapp.domain.utils.SerializationHelper
import java.lang.reflect.Type
import javax.inject.Inject

class SerializationHelperImpl : SerializationHelper {

    private val gson: Gson

    @Inject
    constructor(gson: Gson) {
        this.gson = gson
    }


    override fun toJson(model: Any): String = gson.toJson(model)

    override fun <T> fromJson(json: String, type: Class<T>): T = gson.fromJson(json, type)

    override fun <T> fromJson(json: String, typeOf: Type): T = gson.fromJson(json, typeOf)
}