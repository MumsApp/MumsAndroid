package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecyclePresenter
import javax.inject.Inject

class ShopPresenter : LifecyclePresenter<ShopView> {

    @Inject
    constructor() {

    }

    fun onMenuButtonClick() {
        view?.openMenuDialog()
    }

    fun onDialogSearchButtonClick() {
        view?.closeMenuDialog()
        view?.startSearching()
    }

    fun onSearch(value: String) {

    }
}