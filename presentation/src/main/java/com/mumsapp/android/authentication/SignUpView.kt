package com.mumsapp.android.authentication

import com.mumsapp.android.base.LifecycleView

interface SignUpView: LifecycleView {

    fun showFirstNameError(error: String)

    fun showLastNameError(error: String)

    fun showEmailError(error: String)

    fun showPasswordError(error: String)

    fun showPasswordConfirmationError(error: String)

    fun showTermsAndConditionsError(error: String)

    fun clearErrors()
}