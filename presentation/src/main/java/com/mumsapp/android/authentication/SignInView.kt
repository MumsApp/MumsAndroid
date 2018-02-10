package com.mumsapp.android.authentication

import com.mumsapp.android.base.BaseView

interface SignInView: BaseView {

    fun showEmailError(error: String)

    fun showPasswordError(error: String)

    fun clearErrors()
}