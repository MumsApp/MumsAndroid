package com.mumsapp.android.product

import com.mumsapp.android.base.BaseView

interface RemoveProductView : BaseView {

    fun dismissView()

    fun showProductInformation(imageUrl: String, productName: String, bottomText: String)

    fun deliverResults()
}