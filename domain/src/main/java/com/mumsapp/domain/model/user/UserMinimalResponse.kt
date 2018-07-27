package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class UserMinimalResponse(@SerializedName("id") var id: Int,
                               @SerializedName("name") var name: String,
                               @SerializedName("img") var imgPath: String?) : BaseResponse()