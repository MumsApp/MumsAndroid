package com.mumsapp.android.product

interface EditProductView : BaseProductFormView {

    fun setScreenTitle(title: String)

    fun showInitialDetails(title: String?, category: String?, price: String?, description: String?)
}