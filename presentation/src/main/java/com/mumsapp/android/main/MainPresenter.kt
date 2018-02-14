package com.mumsapp.android.main

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class MainPresenter : BasePresenter<MainView> {

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
}