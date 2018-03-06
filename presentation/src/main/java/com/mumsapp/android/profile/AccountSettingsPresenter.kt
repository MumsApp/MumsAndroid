package com.mumsapp.android.profile

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.domain.interactor.user.SignOutUserUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.EmptyResponse
import javax.inject.Inject

class AccountSettingsPresenter : BasePresenter<AccountSettingsView> {

    private val signOutUseCase: SignOutUserUseCase
    private val activitiesNavigationService: ActivitiesNavigationService

    @Inject
    constructor(signOutUseCase: SignOutUserUseCase,
                activitiesNavigationService: ActivitiesNavigationService) {
        this.signOutUseCase = signOutUseCase
        this.activitiesNavigationService = activitiesNavigationService
    }

    fun onCloseClick() {
        view?.dismissView()
    }

    fun onLogOutClick() {
        addDisposable(
                signOutUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleSignOutSuccess, this::handleSignOutError)
        )
    }

    private fun handleSignOutSuccess(response: EmptyResponse) {
        activitiesNavigationService.openAuthActivity()
        activitiesNavigationService.finishCurrentActivity()
    }

    private fun handleSignOutError(throwable: Throwable) {
        view?.showError(throwable.localizedMessage)
    }
}