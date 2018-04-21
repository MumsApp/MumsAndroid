package com.mumsapp.android.mums_app_offers

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.product.ProductItem
import ss.com.bannerslider.banners.Banner

interface MumsAppOfferDetailsView : LifecycleView {

    fun showImages(images: List<Banner>)

    fun showDetails(title: String, description: String)

    fun showOtherItems(items: List<ProductItem>)

    fun showGetVoucherDialog(title: String, description: String, confirmButtonText: String)
}