package com.mumsapp.android.authentication

import com.mumsapp.android.base.BaseView

interface SignUpView: BaseView {

    fun showFirstNameError(error: String)

    fun showLastNameError(error: String)

    fun showEmailError(error: String)

    fun showPasswordError(error: String)

    fun showPasswordConfirmationError(error: String)
}