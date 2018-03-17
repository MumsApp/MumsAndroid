package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecycleView

interface ShopView : LifecycleView {

    fun openMenuDialog()

    fun closeMenuDialog()

    fun startSearching()
}