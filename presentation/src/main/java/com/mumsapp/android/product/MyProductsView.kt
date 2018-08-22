package com.mumsapp.android.product

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.domain.model.shop.Product

interface MyProductsView : LifecycleView {

    fun showItems(items: List<ReadableShopProduct>,
                  productClickListener: (item: ReadableShopProduct) -> Unit,
                  editClickListener: (item: ReadableShopProduct) -> Unit)
}