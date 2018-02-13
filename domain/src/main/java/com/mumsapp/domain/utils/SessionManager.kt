package com.mumsapp.domain.utils

import com.mumsapp.domain.model.user.UserResponse

interface SessionManager {


    fun loadLoggedUser(): UserResponse?

    fun saveLoggedUser(user: UserResponse?)

    fun clearUserSession()

    fun isUserLogged(): Boolean
}