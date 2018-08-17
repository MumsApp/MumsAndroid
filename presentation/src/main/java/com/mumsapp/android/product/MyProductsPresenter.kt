package com.mumsapp.android.product

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.model.shop.ProductResponse
import javax.inject.Inject

class MyProductsPresenter : LifecyclePresenter<MyProductsView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    override fun start() {
        loadItems()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onUploadProductClick() {
        fragmentsNavigationService.openAddProductFragment(true)
    }

    private fun loadItems() {
//        addDisposable(
//                getShopItemsUseCase.execute(EmptyRequest())
//                        .compose(applyOverlaysToObservable())
//                        .subscribe(this::handleLoadProductsSuccess)
//        )
    }

    private fun handleLoadProductsSuccess(response: ProductResponse) {
//        view?.showItems(response.items, this::handleOnEditClick)
    }

    private fun handleOnEditClick(item: Product) {

    }
}