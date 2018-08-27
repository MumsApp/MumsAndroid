package com.mumsapp.android.product

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.android.util.ShopProductsMapper
import com.mumsapp.domain.interactor.shop.GetFavouriteProductsUseCase
import com.mumsapp.domain.interactor.shop.RemoveProductFromFavouriteUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.shop.ProductsMyResponse
import com.mumsapp.domain.model.shop.ShopProductIdRequest
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class MyWishlistPresenter : LifecyclePresenter<MyWishlistView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val resourceRepository: ResourceRepository
    private val getFavouriteProductsUseCase: GetFavouriteProductsUseCase
    private val shopProductsMapper: ShopProductsMapper
    private val removeProductFromFavouriteUseCase: RemoveProductFromFavouriteUseCase

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                resourceRepository: ResourceRepository,
                getFavouriteProductsUseCase: GetFavouriteProductsUseCase,
                shopProductsMapper: ShopProductsMapper,
                removeProductFromFavouriteUseCase: RemoveProductFromFavouriteUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
        this.getFavouriteProductsUseCase = getFavouriteProductsUseCase
        this.shopProductsMapper = shopProductsMapper
        this.removeProductFromFavouriteUseCase = removeProductFromFavouriteUseCase
    }

    override fun start() {
        loadItems()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    private fun loadItems() {
        addDisposable(
                getFavouriteProductsUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadProductsSuccess)
        )
    }

    private fun handleLoadProductsSuccess(response: ProductsMyResponse) {
        val products = shopProductsMapper.map(response.products)
        view?.showItems(products, this::onProductClick, this::onWishlistCheckboxChanged)
    }

    private fun onProductClick(item: ReadableShopProduct) {
        fragmentsNavigationService.openProductFragment(item.id, true)
    }

    private fun onWishlistCheckboxChanged(item: ReadableShopProduct, value: Boolean) {
        if(!value) {
            val bottomText = resourceRepository.getString(R.string.from_your_wishlist_question_mark)
            view?.openRemoveProductDialog(item, bottomText) {onRemoveConfirmClick(item)}
        }
    }

    private fun onRemoveConfirmClick(item: ReadableShopProduct) {
        val request = ShopProductIdRequest(item.id)
        addDisposable(
                removeProductFromFavouriteUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleProductFavouriteSuccess, this::handleApiError)
        )
    }

    private fun handleProductFavouriteSuccess(response: EmptyResponse) {
        loadItems()
    }
}