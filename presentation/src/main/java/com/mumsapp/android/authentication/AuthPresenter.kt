package com.mumsapp.android.authentication

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.ActivitiesNavigationService_Factory
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class AuthPresenter: BasePresenter<AuthView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val activityNavigationService: ActivitiesNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                activityNavigationService: ActivitiesNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.activityNavigationService = activityNavigationService
    }

    override fun create() {
        fragmentsNavigationService.openAuthMenuFragment(true)
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