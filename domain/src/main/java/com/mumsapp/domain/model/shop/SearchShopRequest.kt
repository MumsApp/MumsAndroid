package com.mumsapp.domain.model.shop

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class SearchShopRequest(@SerializedName("page") var page: Int,
                             @SerializedName("perPage") var perPage: Int,
                             @SerializedName("searchTerm") var searchTerm: String?,
                             @SerializedName("category") var categoryId: Int?,
                             @SerializedName("priceFrom") var priceFrom: Float?,
                             @SerializedName("priceTo") var priceTo: Float?,
                             @SerializedName("userLat") var userLat: Double?,
                             @SerializedName("userLon") var userLon: Double?,
                             @SerializedName("distanceFrom") var distanceFrom: Int?,
                             @SerializedName("distanceTo") var distanceTo: Int?) : BaseRequest()