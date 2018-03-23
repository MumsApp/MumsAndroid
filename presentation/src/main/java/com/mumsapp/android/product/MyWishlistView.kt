package com.mumsapp.android.product

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.product.ProductItem

interface MyWishlistView : LifecycleView {

    fun showItems(items: List<ProductItem>, checkboxChangeListener: (item: ProductItem, value: Boolean) -> Unit)

    fun openRemoveProductDialog(item: ProductItem, bottomText: String, listener: () -> Unit)
}