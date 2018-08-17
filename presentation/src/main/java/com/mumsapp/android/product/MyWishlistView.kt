package com.mumsapp.android.product

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.product.Product

interface MyWishlistView : LifecycleView {

    fun showItems(items: List<Product>, checkboxChangeListener: (item: Product, value: Boolean) -> Unit)

    fun openRemoveProductDialog(item: Product, bottomText: String, listener: () -> Unit)
}