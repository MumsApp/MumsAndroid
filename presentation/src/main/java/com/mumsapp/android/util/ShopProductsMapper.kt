package com.mumsapp.android.util

import com.mumsapp.android.R
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.LocationHelper
import javax.inject.Inject

class ShopProductsMapper {

    private val resourceRepository: ResourceRepository
    private val locationHelper: LocationHelper

    @Inject
    constructor(resourceRepository: ResourceRepository, locationHelper: LocationHelper) {
        this.resourceRepository = resourceRepository
        this.locationHelper = locationHelper
    }

    fun map(products: List<Product>, selectedLat: Double, selectedLon: Double): List<ReadableShopProduct> {

        val readableProducts = ArrayList<ReadableShopProduct>()

        products.forEach {
            val price = resourceRepository.getString(R.string.pounds_price_format, it.price)
            val distance = locationHelper.calculateDistanceInMiles(selectedLat, selectedLon,
                    it.lat, it.lon)
            val formattedMile = resourceRepository.getQuantityString(R.plurals.mile_plural, distance.toInt())
            val readableDistance = "$distance $formattedMile"
            val thumbnailPath = getThumbnail(it)

            val readableProduct = ReadableShopProduct(it, it.name, it.categoryName, price, readableDistance,
                    it.creatorName, it.creatorPhotoPath, thumbnailPath)

            readableProducts += readableProduct
        }

        return readableProducts
    }

    fun getThumbnail(product: Product): String? {
        product.photos.forEach {
            if(it.thumbnail) {
                return it.photoPath
            }
        }

        return null
    }
}