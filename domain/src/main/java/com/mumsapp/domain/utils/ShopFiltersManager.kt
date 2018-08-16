package com.mumsapp.domain.utils

import com.google.android.gms.location.places.Place
import com.mumsapp.domain.model.product.ProductSubcategory

interface ShopFiltersManager {

    fun setSubcategory(name: ProductSubcategory?)

    fun getSubcategory(): ProductSubcategory?

    fun setGiveItForFree(value: Boolean?)

    fun getGiveItForFree(): Boolean?

    fun setMinPrice(price: Int?)

    fun getMinPrice(): Int?

    fun setMaxPrice(price: Int?)

    fun getMaxPrice(): Int?

    fun setPlace(place: Place?)

    fun getPlace(): Place?

    fun setMinDistance(distance: Int?)

    fun getMinDistance(): Int?

    fun setMaxDistance(distance: Int?)

    fun getMaxDistance(): Int?

    fun clear()
}