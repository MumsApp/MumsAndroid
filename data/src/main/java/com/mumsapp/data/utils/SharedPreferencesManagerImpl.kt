package com.mumsapp.data.utils

import android.content.SharedPreferences
import com.mumsapp.domain.utils.SharedPreferencesManager
import javax.inject.Inject

class SharedPreferencesManagerImpl : SharedPreferencesManager {

    private val sharedPreferences: SharedPreferences

    @Inject
    constructor(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    override fun putString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun putStringMap(map: Map<String, String>?) {
        val editor = sharedPreferences.edit()
        if (map != null) {
            for (key in map.keys) {
                editor.putString(key, map[key])
            }
        }
        editor.apply()
    }

    override fun clearString(key: String) = putString(key, null)

    override fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun putBoolean(key: String, newValue: Boolean) {
        sharedPreferences.edit().putBoolean(key, newValue).apply()
    }
}