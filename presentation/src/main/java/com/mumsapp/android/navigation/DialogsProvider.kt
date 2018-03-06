package com.mumsapp.android.navigation

import com.mumsapp.android.base.BaseActivity
import com.mumsapp.android.location.LocationSelectingDialog
import com.mumsapp.android.profile.AccountSettingsDialog
import javax.inject.Inject

class DialogsProvider {

    private val activity: BaseActivity

    @Inject
    constructor(activity: BaseActivity) {
        this.activity = activity
    }

    fun createAccountSettingsDialog() = AccountSettingsDialog(activity)

    fun createLocationSelectingDialog() = LocationSelectingDialog(activity)
}