package com.mumsapp.domain.model.product

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class ProductSubcategory(@SerializedName("id") var id: Int,
                              @SerializedName("name") var name: String) : BaseResponse()