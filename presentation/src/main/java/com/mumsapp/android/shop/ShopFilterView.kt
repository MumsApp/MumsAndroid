package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecycleView

interface ShopFilterView : LifecycleView {

    fun setCategoryName(categoryName: String?)

    fun enablePriceSelection()

    fun disablePriceSelection()

    fun showEditLocationScreen()

    fun showLocationName(name: String)

    fun setGiveItForFree(value: Boolean)

    fun getGiveItForFree(): Boolean

    fun setPrice(min: Int, max: Int)

    fun getMinPrice(): Int

    fun getMaxPrice(): Int

    fun setDistance(min: Int, max: Int)

    fun getMinDistance(): Int

    fun getMaxDistance(): Int
}