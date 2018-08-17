package com.mumsapp.domain.model.shop

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse
import org.threeten.bp.ZonedDateTime

data class ProductResponse(var items: List<Product>) : BaseResponse()

data class ProductResponseData(@SerializedName("pages") var pages: Int,
                               @SerializedName("products") var products: List<Product>) : BaseResponse()

data class Product(@SerializedName("id") var id: Int,
                   @SerializedName("name") var name: String,
                   @SerializedName("description") var description: String,
                   @SerializedName("price") var price: Float,
                   @SerializedName("lat") var lat: Double,
                   @SerializedName("lon") var lon: Double,
                   @SerializedName("pointName") var pointName: String?,
                   @SerializedName("category") var categoryId: Int,
                   @SerializedName("categoryName") var categoryName: String,
                   @SerializedName("creatorId") var creatorId: Int,
                   @SerializedName("creatorName") var creatorName: String,
                   @SerializedName("creatorPhoto") var creatorPhotoPath: String?,
                   @SerializedName("creationDate") var creationDate: ZonedDateTime,
                   @SerializedName("isUserFavourite") var isUserFavourite: Boolean,
                   @SerializedName("photos") var photos: List<ProductPhoto>) : BaseResponse()

data class ProductPhoto(@SerializedName("id") var id: Int,
                        @SerializedName("thumbnail") var thumbnail: Boolean,
                        @SerializedName("src") var photoPath: String)