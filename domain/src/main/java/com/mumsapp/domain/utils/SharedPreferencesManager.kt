package com.mumsapp.domain.utils

interface SharedPreferencesManager {

    fun getString(key: String, defaultValue: String?): String?

    fun putString(key: String, value: String?)

    fun putStringMap(map: Map<String, String>?)

    fun clearString(key: String)

    fun putInt(key: String, value: Int)

    fun getInt(key: String, defaultValue: Int): Int

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun putBoolean(key: String, newValue: Boolean)
}