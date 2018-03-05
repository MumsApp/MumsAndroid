package com.mumsapp.android.authentication

import com.mumsapp.android.base.LifecycleView

interface SignInView: LifecycleView {

    fun showEmailError(error: String)

    fun showPasswordError(error: String)

    fun clearErrors()
}