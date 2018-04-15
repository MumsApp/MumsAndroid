package com.mumsapp.android.mums_app_offers

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class MumsAppOffersPresenter : LifecyclePresenter<MumsAppOffersView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }
}