package com.mumsapp.android.navigation

import com.mumsapp.android.base.BaseActivity
import com.mumsapp.android.profile.AccountSettingsDialog
import com.mumsapp.android.profile.UserDetailsSettingsDialog
import javax.inject.Inject

class DialogsProvider {

    private val activity: BaseActivity

    @Inject
    constructor(activity: BaseActivity) {
        this.activity = activity
    }

    fun createAccountSettingsDialog() = AccountSettingsDialog(activity)

    fun createUserDetailsSettingsDialog() = UserDetailsSettingsDialog(activity)
}