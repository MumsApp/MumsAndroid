package com.mumsapp.android.profile

import com.mumsapp.android.base.LifecycleView

interface MyProfileView: LifecycleView {

    fun showProfileInfo(name: String, description: String)

    fun loadAvatar(url: String)

    fun showAccountSettingsDialog()

    fun showUserDetailsSettingsDialog()

    fun showEditLocationScreen()

    fun showLocation()

    fun showNewLocation(latitude: String, longitude: String, name: String)

    fun hideLocation()
}