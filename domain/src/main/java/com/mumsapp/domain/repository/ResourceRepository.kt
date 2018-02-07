package com.mumsapp.domain.repository

interface ResourceRepository {

    fun getString(stringResId: Int): String

    fun getString(stringResId: Int, vararg formatArgs: Any): String

    fun getString(name: String): String

    fun getStringArray(stringResId: Int): Array<String>

    fun getQuantityString(pluralResId: Int, count: Int, vararg formatArgs: Any): String

    fun getQuantityString(pluralResId: Int, count: Int): String

    fun getColor(colorResId: Int): Int

    fun getInteger(resId: Int): Int
}