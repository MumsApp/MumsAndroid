package com.mumsapp.android.product

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.domain.model.product.ProductItem
import javax.inject.Inject

class RemoveProductPresenter : BasePresenter<RemoveProductView> {

    lateinit var productItem: ProductItem
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

    fun setArguments(productItem: ProductItem, bottomText: String) {
        this.productItem = productItem
        this.bottomText = bottomText
    }

    override fun start() {
        view?.showProductInformation("", productItem.name, bottomText)
    }
}