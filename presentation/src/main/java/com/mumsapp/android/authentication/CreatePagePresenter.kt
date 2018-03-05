package com.mumsapp.android.authentication

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class CreatePagePresenter: LifecyclePresenter<CreatePageView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }
}