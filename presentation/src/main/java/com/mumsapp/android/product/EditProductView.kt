package com.mumsapp.android.product

interface EditProductView : BaseProductFormView {

    fun showInitialDetails(title: String?, category: String?, price: String?, description: String?)
}