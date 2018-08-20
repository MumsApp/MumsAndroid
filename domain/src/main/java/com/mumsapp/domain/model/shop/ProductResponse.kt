package com.mumsapp.domain.model.shop

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class ProductResponse(@SerializedName("data") var product: Product) : BaseResponse()