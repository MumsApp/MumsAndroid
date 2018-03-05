package com.mumsapp.android.profile

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.domain.interactor.user.SignOutUserUseCase
import javax.inject.Inject

class AccountSettingsPresenter : BasePresenter<AccountSettingsView> {

    private val signOutUseCase: SignOutUserUseCase

    @Inject
    constructor(signOutUseCase: SignOutUserUseCase) {
        this.signOutUseCase = signOutUseCase
    }
}