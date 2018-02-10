package com.mumsapp.domain.utils

import java.lang.reflect.Type

interface SerializationHelper {

    fun toJson(model: Any): String

    fun <T> fromJson(json: String, type: Class<T>): T

    fun <T> fromJson(json: String, typeOf: Type): T
}