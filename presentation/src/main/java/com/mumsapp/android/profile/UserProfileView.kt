package com.mumsapp.android.profile

import com.mumsapp.android.base.LifecycleView

interface UserProfileView : LifecycleView {

    fun showProfileInfo(name: String, description: String)

    fun loadAvatar(url: String)

    fun showNumberOfKids(text: String)

    fun showLocation(latitude: String, longitude: String, name: String)
}