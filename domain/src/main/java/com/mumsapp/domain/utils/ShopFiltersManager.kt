package com.mumsapp.domain.utils

import com.mumsapp.domain.model.product.ProductSubcategory

interface ShopFiltersManager {

    fun setSubcategoryId(id: Int?)

    fun getSubcategoryId(): Int?

    fun setSubcategoryName(name: String?)

    fun getSubcategoryName(): String?

    fun setGiveItForFree(value: Boolean?)

    fun getGiveItForFree(): Boolean?

    fun setMinPrice(price: Float?)

    fun getMinPrice(): Float?

    fun setMaxPrice(price: Float?)

    fun getMaxPrice(): Float?

    fun setMinDistance(distance: Int?)

    fun getMinDistance(): Int?

    fun setMaxDistance(distance: Int?)

    fun getMaxDistance(): Int?

    fun clear()
}