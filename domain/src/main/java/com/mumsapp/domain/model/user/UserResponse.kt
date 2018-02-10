package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class UserResponse(@SerializedName("id") var id: Int,
                        @SerializedName("first_name") var firstName: String,
                        @SerializedName("last_name") var lastName: String,
                        @SerializedName("email") var email: String) : BaseResponse()