package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.shop.GetShopItemsUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.product.ProductItem
import com.mumsapp.domain.model.product.TemplateProductResponse
import javax.inject.Inject

class ShopPresenter : LifecyclePresenter<ShopView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val getShopItemsUseCase: GetShopItemsUseCase

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                getShopItemsUseCase: GetShopItemsUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.getShopItemsUseCase = getShopItemsUseCase
    }

    override fun start() {
        loadItems()
    }

    fun onMenuButtonClick() {
        view?.openMenuDialog()
    }

    fun onFilterClick() {
        fragmentsNavigationService.openShopFilterFragment(true)
    }

    fun onDialogSearchButtonClick() {
        view?.closeMenuDialog()
        view?.startSearching()
    }

    fun onSearch(value: String) {

    }

    fun onProductClick(product: ProductItem) {
        fragmentsNavigationService.openProductFragment(product.id, true)
    }

    private fun loadItems() {
        addDisposable(
                getShopItemsUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadProductsSuccess)
        )
    }

    private fun handleLoadProductsSuccess(response: TemplateProductResponse) {
        view?.showItems(response.items)
    }
}