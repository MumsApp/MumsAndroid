package com.mumsapp.android.product

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.domain.model.product.ProductItem
import javax.inject.Inject

class SelectImageSourcePresenter : BasePresenter<SelectImageSourceView> {

    lateinit var productItem: ProductItem
    lateinit var bottomText: String

    @Inject
    constructor()

    fun onCloseClick() {
        view?.dismissView()
    }

    fun onGalleryClick() {
        view?.deliverGalleryResults()
        view?.dismissView()
    }

    fun onCameraClick() {
        view?.deliverCameraResults()
        view?.dismissView()
    }
}