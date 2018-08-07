package com.mumsapp.android.profile

import android.net.Uri
import com.mumsapp.android.base.LifecycleView

interface UserProfileView : LifecycleView {

    fun showProfileInfo(name: String, description: String?)

    fun loadAvatar(url: String)

    fun showNumberOfKids(text: String)

    fun showLocation(latitude: String, longitude: String, name: String)

    fun setAddContactVisibility(visible: Boolean)

    fun setRemoveContactVisibility(visible: Boolean)

    fun showRemoveUserDialog(avatarUri: Uri?, name: String, title: String, description: String,
                             confirmButtonText: String, cancelButtonText: String,
                             confirmationListener: () -> Unit, cancelListener: () -> Unit)
}