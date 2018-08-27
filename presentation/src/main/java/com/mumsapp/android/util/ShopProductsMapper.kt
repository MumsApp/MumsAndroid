package com.mumsapp.android.util

import com.mumsapp.android.R
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.LocationHelper
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class ShopProductsMapper {

    private val resourceRepository: ResourceRepository
    private val locationHelper: LocationHelper

    @Inject
    constructor(resourceRepository: ResourceRepository, locationHelper: LocationHelper) {
        this.resourceRepository = resourceRepository
        this.locationHelper = locationHelper
    }

    fun map(products: List<Product>, selectedLat: Double? = null, selectedLon: Double? = null): List<ReadableShopProduct> {

        val readableProducts = ArrayList<ReadableShopProduct>()

        products.forEach {
            val price = resourceRepository.getString(R.string.pounds_price_format, it.price)

            val readableDistance = calculateDistance(it, selectedLat, selectedLon)
            val thumbnailPath = getThumbnail(it)

            val readableProduct = ReadableShopProduct(it, it.id, it.name, it.categoryName, price, readableDistance,
                    it.creatorName, it.isUserFavourite, it.creatorPhotoPath, thumbnailPath)

            readableProducts += readableProduct
        }

        return readableProducts
    }

    fun getThumbnail(product: Product): String? {
        product.photos.forEach {
            return it.photoPath
        }

        return null
    }

    private fun calculateDistance(product: Product, selectedLat: Double?, selectedLon: Double?): String {
        return if(selectedLat == null || selectedLon == null) {
            ""
        } else {
            val distance = locationHelper.calculateDistanceInMiles(selectedLat, selectedLon,
                    product.lat, product.lon).roundToInt()
            val formattedMile = resourceRepository.getQuantityString(R.plurals.mile_plural, distance.toInt())

            "$distance $formattedMile"
        }
    }
}