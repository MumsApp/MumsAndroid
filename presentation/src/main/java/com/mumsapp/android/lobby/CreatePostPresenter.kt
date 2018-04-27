package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class CreatePostPresenter : LifecyclePresenter<CreatePostView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    private var lobbyCategoryId = 0

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun setArguments(lobbyCategoryId: Int) {
        this.lobbyCategoryId = lobbyCategoryId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onDoneClick() {
        fragmentsNavigationService.popFragment()
    }
}