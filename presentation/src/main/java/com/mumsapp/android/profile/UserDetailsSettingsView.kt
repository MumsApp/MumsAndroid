package com.mumsapp.android.profile

import com.mumsapp.android.base.BaseView

interface UserDetailsSettingsView : BaseView {

    fun dismissView()

    fun showInitialData(firstName: String?, lastName: String?, description: String?)

    fun showFirstNameError(error: String)

    fun showLastNameError(error: String)

    fun deliverResults(firstName: String, lastName: String, description: String)
}