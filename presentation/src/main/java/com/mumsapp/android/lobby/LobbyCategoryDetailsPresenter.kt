package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class LobbyCategoryDetailsPresenter : LifecyclePresenter<LobbyCategoryDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    private var categoryId: Int? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun setArguments(categoryId: Int?) {
        this.categoryId = categoryId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onCreatePostClick() {

    }
}