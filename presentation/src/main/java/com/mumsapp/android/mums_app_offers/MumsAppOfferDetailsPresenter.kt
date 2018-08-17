package com.mumsapp.android.mums_app_offers

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.model.shop.ProductResponse
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.DrawableBanner
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

    override fun start() {
        showMockedData()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onGetVoucherClick() {
        view?.showGetVoucherDialog("5$ Discount on Nappy.com", "Promotion description", "Got it!")
    }

    fun onFollowClick() {

    }

    fun onGetVoucherConfirmationClick() {

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
//        addDisposable(
//                getShopItemsUseCase.execute(EmptyRequest())
//                        .compose(applyOverlaysToObservable())
//                        .subscribe(this::handleLoadProductsSuccess)
//        )
    }

    private fun handleLoadProductsSuccess(response: ProductResponse) {
//        view?.showOtherItems(response.items)
    }
}