package com.mumsapp.android.product

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.product.Product

interface MyProductsView : LifecycleView {

    fun showItems(items: List<Product>, editClickListener: (item: Product) -> Unit)
}