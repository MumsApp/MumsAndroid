package com.mumsapp.android.mums_app_offers

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class MumsAppOfferDetailsPresenter : LifecyclePresenter<MumsAppOfferDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val dialogsProvider: DialogsProvider

    private var productId: Int? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                dialogsProvider: DialogsProvider) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.dialogsProvider = dialogsProvider
    }

    fun setArguments(productId: Int?) {
        this.productId = productId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }
}