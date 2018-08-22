package com.mumsapp.android.product

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.android.util.ShopProductsMapper
import com.mumsapp.domain.interactor.shop.GetMyProductsUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.shop.ProductsMyResponse
import javax.inject.Inject

class MyProductsPresenter : LifecyclePresenter<MyProductsView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val getMyProductsUseCase: GetMyProductsUseCase
    private val shopProductsMapper: ShopProductsMapper

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                getMyProductsUseCase: GetMyProductsUseCase,
                shopProductsMapper: ShopProductsMapper) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.getMyProductsUseCase = getMyProductsUseCase
        this.shopProductsMapper = shopProductsMapper
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

    private fun onProductClick(product: ReadableShopProduct) {

    }

    private fun onEditClick(item: ReadableShopProduct) {

    }

    private fun loadItems() {
        addDisposable(
                getMyProductsUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadProductsSuccess)
        )
    }

    private fun handleLoadProductsSuccess(response: ProductsMyResponse) {
        val products = shopProductsMapper.map(response.products)
        view?.showItems(products, this::onProductClick, this::onEditClick)
    }
}