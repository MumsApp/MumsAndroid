package com.mumsapp.android.main

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.utils.EMERGENCY_BUTTON_VALUE_KEY
import com.mumsapp.domain.utils.SessionManager
import com.mumsapp.domain.utils.SharedPreferencesManager
import javax.inject.Inject

class MainPresenter : LifecyclePresenter<MainView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val activityNavigationService: ActivitiesNavigationService
    private val sharedPreferencesManager: SharedPreferencesManager
    private val sessionManager: SessionManager

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                activityNavigationService: ActivitiesNavigationService,
                sharedPreferencesManager: SharedPreferencesManager, sessionManager: SessionManager) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.activityNavigationService = activityNavigationService
        this.sharedPreferencesManager = sharedPreferencesManager
        this.sessionManager = sessionManager
    }

    override fun start() {
        setupEmergencyButton()
    }

    fun handleBackOrDelegateToSystem(): Boolean {
        if(fragmentsNavigationService.getBackStackCount() <= 1) {
            activityNavigationService.finishCurrentActivity()
            return false
        }

        val topFragment = fragmentsNavigationService.findTopFragment()

        if(topFragment != null && topFragment.goingBack()) {
            fragmentsNavigationService.popFragment()
        } else {
            return true
        }

        return false
    }

    fun onSessionExpiredButtonClick() {
        sessionManager.clearUserSession()
        activityNavigationService.openAuthActivity()
        activityNavigationService.finishCurrentActivity()
    }

    fun onOpenMenuClick() {
        view?.showMenu()
        view?.hideEmergencyButton()
    }

    fun onCloseMenuClick() {
        view?.hideMenu()
        view?.showEmergencyButton()
    }

    fun onMeClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openMyProfileFragment(true)
        view?.hideMenu()
    }

    fun onLobbyClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openLobbyFragment(true)
        view?.hideMenu()
    }

    fun onTalkClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openChatFragment(true)
        view?.hideMenu()
    }

    fun onShopClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openShopFragment(true)
        view?.hideMenu()
    }

    fun onOffersClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openMumsAppOffersFragment(true)
        view?.hideMenu()
    }

    fun onHolidayClick() {

    }

    fun onHealthClick() {

    }

    fun onAddClick() {

    }

    private fun setupEmergencyButton() {
        val enabled = sharedPreferencesManager.getBoolean(EMERGENCY_BUTTON_VALUE_KEY, true)

        if(enabled) {
            view?.showEmergencyButton()
        } else {
            view?.hideEmergencyButton()
        }
    }
}