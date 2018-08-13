package com.mumsapp.domain.utils

import com.google.android.gms.location.places.Place

interface ShopFiltersManager {

    fun setSubcategoryId(id: Int?)

    fun getSubcategoryId(): Int?

    fun setSubcategoryName(name: String?)

    fun getSubcategoryName(): String?

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