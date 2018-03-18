package com.mumsapp.android.authentication

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.utils.SessionManager
import javax.inject.Inject

class AuthPresenter: LifecyclePresenter<AuthView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val activityNavigationService: ActivitiesNavigationService
    private val sessionManager: SessionManager

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                activityNavigationService: ActivitiesNavigationService,
                sessionManager: SessionManager) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.activityNavigationService = activityNavigationService
        this.sessionManager = sessionManager
    }

    override fun create() {
        if(sessionManager.isUserLogged()) {
            activityNavigationService.openMainActivity()
            activityNavigationService.finishCurrentActivity()
        } else {
            fragmentsNavigationService.openAuthMenuFragment(true)
        }
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
}