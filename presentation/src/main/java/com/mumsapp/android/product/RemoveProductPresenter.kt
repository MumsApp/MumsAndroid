package com.mumsapp.android.product

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.domain.model.shop.Product
import javax.inject.Inject

class RemoveProductPresenter : BasePresenter<RemoveProductView> {

    lateinit var productItem: ReadableShopProduct
    lateinit var bottomText: String

    @Inject
    constructor()

    fun onCloseClick() {
        view?.dismissView()
    }

    fun onYesButtonClick() {
        view?.deliverResults()
        view?.dismissView()
    }

    fun setArguments(productItem: ReadableShopProduct, bottomText: String) {
        this.productItem = productItem
        this.bottomText = bottomText
    }

    override fun start() {
        view?.showProductInformation(productItem.thumbnailPath, productItem.name, bottomText)
    }
}