package com.mumsapp.domain.model.shop

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class ProductsResponse(@SerializedName("data") var data: ProductsResponseData) : BaseResponse()

data class ProductsResponseData(@SerializedName("pages") var pages: Int,
                                @SerializedName("products") var products: List<Product>) : BaseResponse()

