package com.mumsapp.android.profile

import com.mumsapp.android.base.BaseView

interface AccountSettingsView : BaseView {

    fun dismissView()

    fun setEmergencyButtonValue(value: Boolean)
}