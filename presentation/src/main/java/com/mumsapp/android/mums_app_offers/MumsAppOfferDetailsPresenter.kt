package com.mumsapp.android.mums_app_offers

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.shop.GetShopItemsUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.product.TemplateProductResponse
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.DrawableBanner
import javax.inject.Inject

class MumsAppOfferDetailsPresenter : LifecyclePresenter<MumsAppOfferDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val dialogsProvider: DialogsProvider
    private val getShopItemsUseCase: GetShopItemsUseCase

    private var productId: Int? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                dialogsProvider: DialogsProvider,
                getShopItemsUseCase: GetShopItemsUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.dialogsProvider = dialogsProvider
        this.getShopItemsUseCase = getShopItemsUseCase
    }

    fun setArguments(productId: Int?) {
        this.productId = productId
    }

    override fun start() {
        showMockedData()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onGetVoucherClick() {

    }

    fun onFollowClick() {

    }

    private fun showMockedData() {
        val banners = ArrayList<Banner>()
        banners.add(DrawableBanner(R.drawable.ic_image_placeholder))
        banners.add(DrawableBanner(R.drawable.ic_image_placeholder))
        banners.add(DrawableBanner(R.drawable.ic_image_placeholder))
        view?.showImages(banners)

        view?.showDetails("Offer title", "Offer description")


        loadShopItems()
    }

    private fun loadShopItems() {
        addDisposable(
                getShopItemsUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadProductsSuccess)
        )
    }

    private fun handleLoadProductsSuccess(response: TemplateProductResponse) {
        view?.showOtherItems(response.items)
    }
}