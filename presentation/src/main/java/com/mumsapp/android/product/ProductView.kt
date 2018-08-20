package com.mumsapp.android.product

import com.mumsapp.android.base.LifecycleView
import ss.com.bannerslider.banners.Banner

interface ProductView : LifecycleView {

    fun setProductDetails(images: List<Banner>, productName: String, category: String, avatarUrl: String?, userName: String,
                          price: String, distance: String?, description: String)

    fun setLocationDetails(locationName: String?, latitude: Double, longitude: Double)
}