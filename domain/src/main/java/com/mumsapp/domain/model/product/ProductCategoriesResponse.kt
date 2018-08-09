package com.mumsapp.domain.model.product

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class ProductCategoriesResponse(@SerializedName("data") var data : List<ProductCategory>)
    : BaseResponse()