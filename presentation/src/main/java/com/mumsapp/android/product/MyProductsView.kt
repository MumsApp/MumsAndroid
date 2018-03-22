package com.mumsapp.android.product

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.product.ProductItem

interface MyProductsView : LifecycleView {

    fun showItems(items: List<ProductItem>, editClickListener: (item: ProductItem) -> Unit)
}