package com.mumsapp.android.product

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.model.shop.ProductsResponse
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class MyWishlistPresenter : LifecyclePresenter<MyWishlistView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val resourceRepository: ResourceRepository

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                resourceRepository: ResourceRepository) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
    }

    override fun start() {
        loadItems()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    private fun loadItems() {
//        addDisposable(
//                getShopItemsUseCase.execute(EmptyRequest())
//                        .compose(applyOverlaysToObservable())
//                        .subscribe(this::handleLoadProductsSuccess)
//        )
    }

    private fun handleLoadProductsSuccess(response: ProductsResponse) {
//        view?.showItems(response.items, this::onWishlistCheckboxChanged)
    }

    private fun onWishlistCheckboxChanged(item: Product, value: Boolean) {
        if(!value) {
            val bottomText = resourceRepository.getString(R.string.from_your_wishlist_question_mark)
            view?.openRemoveProductDialog(item, bottomText, {

            })
        }
    }
}