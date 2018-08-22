package com.mumsapp.android.product

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.domain.model.shop.Product

interface MyWishlistView : LifecycleView {

    fun showItems(items: List<ReadableShopProduct>, checkboxChangeListener: (item: ReadableShopProduct, value: Boolean) -> Unit)

    fun openRemoveProductDialog(item: ReadableShopProduct, bottomText: String, listener: () -> Unit)
}