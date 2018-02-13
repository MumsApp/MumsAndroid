package com.mumsapp.data.utils

import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.utils.SerializationHelper
import com.mumsapp.domain.utils.SessionManager
import com.mumsapp.domain.utils.SharedPreferencesManager
import javax.inject.Inject

class SessionManagerImpl : SessionManager {

    private val sharedPreferencesManager: SharedPreferencesManager
    private val serializationHelper: SerializationHelper
    private val KEY_USER_MODEL = "USER_MODEL"
    private var user: UserResponse? = null

    @Inject
    constructor(sharedPreferencesManager: SharedPreferencesManager, serializationHelper: SerializationHelper) {
        this.sharedPreferencesManager = sharedPreferencesManager
        this.serializationHelper = serializationHelper
    }

    override fun loadLoggedUser(): UserResponse? {
        if (user == null) {
            val userJson = sharedPreferencesManager.getString(KEY_USER_MODEL, null)

            if (userJson != null) {
                user = serializationHelper.fromJson(userJson, UserResponse::class.java)
            }
        }
        return user
    }

    override fun saveLoggedUser(user: UserResponse?) {
        var userJson: String? = null

        if (user != null) {
            userJson = serializationHelper.toJson(user)
        }
        sharedPreferencesManager.putString(KEY_USER_MODEL, userJson)
        this.user = user
    }

    override fun clearUserSession() {
        sharedPreferencesManager.putString(KEY_USER_MODEL, null)
        user = null
    }

    override fun isUserLogged(): Boolean = loadLoggedUser() != null
}