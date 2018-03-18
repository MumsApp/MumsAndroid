package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.product.ProductItem

interface ShopView : LifecycleView {

    fun openMenuDialog()

    fun closeMenuDialog()

    fun startSearching()

    fun showItems(items: List<ProductItem>)
}