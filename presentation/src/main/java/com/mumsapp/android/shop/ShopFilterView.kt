package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecycleView

interface ShopFilterView : LifecycleView {

    fun setCategoryName(categoryName: String?)
}