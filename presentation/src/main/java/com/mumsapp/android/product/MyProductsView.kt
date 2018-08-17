package com.mumsapp.android.product

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.shop.Product

interface MyProductsView : LifecycleView {

    fun showItems(items: List<Product>, editClickListener: (item: Product) -> Unit)
}