package com.mumsapp.android.profile

import com.mumsapp.android.base.BaseView

interface MyProfileView: BaseView {

    fun showProfileInfo(name: String, description: String)

    fun loadAvatar(url: String)

    fun showAccountSettingsDialog()
}