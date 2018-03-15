package com.mumsapp.android.main

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class MainPresenter : LifecyclePresenter<MainView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val activityNavigationService: ActivitiesNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                activityNavigationService: ActivitiesNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.activityNavigationService = activityNavigationService
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
        activityNavigationService.openAuthActivity()
        activityNavigationService.finishCurrentActivity()
    }

    fun onOpenMenuClick() {
        view?.showMenu()
    }

    fun onCloseMenuClick() {
        view?.hideMenu()
    }

    fun onWhereFindClick() {

    }

    fun onLobbyClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openLobbyFragment(true)
        view?.hideMenu()
    }

    fun onTalkClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openChatListFragment(true)
        view?.hideMenu()
    }

    fun onMeClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openMyProfileFragment(true)
        view?.hideMenu()
    }

    fun onShopClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openShopFragment(true)
        view?.hideMenu()
    }

    fun onOffersClick() {

    }
}