package com.mumsapp.domain.model.shop

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class ProductsMyResponse(@SerializedName("data") var products: List<Product>) : BaseResponse()