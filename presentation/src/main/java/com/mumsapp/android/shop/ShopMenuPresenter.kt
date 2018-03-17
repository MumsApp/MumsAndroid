package com.mumsapp.android.shop

import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class ShopMenuPresenter : BasePresenter<ShopMenuView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun onCloseClick() {
        view?.dismissView()
    }

    fun onSearchClick() {
        view?.deliverSearchResults()
    }

    fun onMyProductsClick() {
        view?.dismissView()
        fragmentsNavigationService.openMyProductsFragment(true)
    }

    fun onMywishlistClick() {
        view?.dismissView()
        fragmentsNavigationService.openMyWishlistFragment(true)
    }
}