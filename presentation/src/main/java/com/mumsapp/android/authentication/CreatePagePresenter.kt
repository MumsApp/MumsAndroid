package com.mumsapp.android.authentication

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class CreatePagePresenter: BasePresenter<CreatePageView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }
}