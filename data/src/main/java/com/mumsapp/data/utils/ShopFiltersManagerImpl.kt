package com.mumsapp.data.utils

import com.google.android.gms.location.places.Place
import com.mumsapp.domain.model.shop.ProductSubcategory
import com.mumsapp.domain.utils.ShopFiltersManager
import javax.inject.Inject

class ShopFiltersManagerImpl : ShopFiltersManager {

    private var subcategory: ProductSubcategory? = null
    private var giveItForFree: Boolean? = null
    private var minPrice: Int? = null
    private var maxPrice: Int? = null
    private var place: Place? = null
    private var minDistance: Int? = null
    private var maxDistance: Int? = null

    @Inject
    constructor()

    override fun setSubcategory(subcategory: ProductSubcategory?) {
        this.subcategory = subcategory
    }

    override fun getSubcategory() = subcategory

    override fun setGiveItForFree(value: Boolean?) {
        giveItForFree = value
    }

    override fun getGiveItForFree() = giveItForFree

    override fun setMinPrice(price: Int?) {
        minPrice = price
    }

    override fun getMinPrice() = minPrice

    override fun setMaxPrice(price: Int?) {
        maxPrice = price
    }

    override fun getMaxPrice() = maxPrice

    override fun setPlace(place: Place?) {
        this.place = place
    }

    override fun getPlace() = place

    override fun setMinDistance(distance: Int?) {
        minDistance = distance
    }

    override fun getMinDistance() = minDistance

    override fun setMaxDistance(distance: Int?) {
        maxDistance = distance
    }

    override fun getMaxDistance() = maxDistance

    override fun clear() {
        subcategory = null
        giveItForFree = null
        minPrice = null
        maxPrice = null
        minDistance = null
        maxDistance = null
    }
}