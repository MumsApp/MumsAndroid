package com.mumsapp.android.profile

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.domain.utils.EMERGENCY_BUTTON_VALUE_KEY
import com.mumsapp.domain.interactor.user.SignOutUserUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.utils.SharedPreferencesManager
import javax.inject.Inject

class AccountSettingsPresenter : BasePresenter<AccountSettingsView> {

    private val signOutUseCase: SignOutUserUseCase
    private val activitiesNavigationService: ActivitiesNavigationService
    private val sharedPreferencesManager: SharedPreferencesManager

    @Inject
    constructor(signOutUseCase: SignOutUserUseCase,
                activitiesNavigationService: ActivitiesNavigationService,
                sharedPreferencesManager: SharedPreferencesManager) {
        this.signOutUseCase = signOutUseCase
        this.activitiesNavigationService = activitiesNavigationService
        this.sharedPreferencesManager = sharedPreferencesManager
    }

    override fun start() {
        setupEmergencyValue()
    }

    fun onCloseClick() {
        view?.dismissView()
    }

    fun onEmergencyButtonValueChanged(value: Boolean) {
        sharedPreferencesManager.putBoolean(EMERGENCY_BUTTON_VALUE_KEY, value)
        //TODO: Notify activity about new emergency button value
    }

    fun onLogOutClick() {
        addDisposable(
                signOutUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleSignOutSuccess, this::handleSignOutError)
        )
    }

    private fun setupEmergencyValue() {
        val value = sharedPreferencesManager.getBoolean(EMERGENCY_BUTTON_VALUE_KEY, true)
        view?.setEmergencyButtonValue(value)
    }

    private fun handleSignOutSuccess(response: EmptyResponse) {
        activitiesNavigationService.openAuthActivity()
        activitiesNavigationService.finishCurrentActivity()
    }

    private fun handleSignOutError(throwable: Throwable) {
        view?.showError(throwable.localizedMessage)
    }
}