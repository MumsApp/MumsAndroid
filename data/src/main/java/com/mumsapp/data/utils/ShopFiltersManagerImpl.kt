package com.mumsapp.data.utils

import com.mumsapp.domain.utils.ShopFiltersManager
import javax.inject.Inject

class ShopFiltersManagerImpl : ShopFiltersManager {

    private var subcategoryId: Int? = null
    private var subcategoryName: String? = null
    private var giveItForFree: Boolean? = null
    private var minPrice: Float? = null
    private var maxPrice: Float? = null
    private var minDistance: Int? = null
    private var maxDistance: Int? = null

    @Inject
    constructor()

    override fun setSubcategoryId(id: Int?) {
        subcategoryId = id
    }

    override fun getSubcategoryId() = subcategoryId

    override fun setSubcategoryName(name: String?) {
        subcategoryName = name
    }

    override fun getSubcategoryName() = subcategoryName

    override fun setGiveItForFree(value: Boolean?) {
        giveItForFree = value
    }

    override fun getGiveItForFree() = giveItForFree

    override fun setMinPrice(price: Float?) {
        minPrice = price
    }

    override fun getMinPrice() = minPrice

    override fun setMaxPrice(price: Float?) {
        maxPrice = price
    }

    override fun getMaxPrice() = maxPrice

    override fun setMinDistance(distance: Int?) {
        minDistance = distance
    }

    override fun getMinDistance() = minDistance

    override fun setMaxDistance(distance: Int?) {
        maxDistance = distance
    }

    override fun getMaxDistance() = maxDistance

    override fun clear() {
        subcategoryId = null
        subcategoryName = null
        giveItForFree = null
        minPrice = null
        maxPrice = null
        minDistance = null
        maxDistance = null
    }
}