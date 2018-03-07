package com.mumsapp.android.profile

import com.mumsapp.android.base.BasePresenter
import javax.inject.Inject

class UserDetailsSettingsPresenter : BasePresenter<UserDetailsSettingsView> {

    @Inject
    constructor() {

    }

    fun onCloseClick() {
        view?.dismissView()
    }
}