package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.shop.Product

interface ShopView : LifecycleView {

    fun openMenuDialog()

    fun closeMenuDialog()

    fun startSearching()

    fun showFilterValues(category: String, price: String, distance: String)

    fun showItems(items: List<ReadableShopProduct>, clickListener: (product: ReadableShopProduct) -> Unit,
                  checkboxChangeListener: (item: ReadableShopProduct, value: Boolean) -> Unit)
}