package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class FacebookSignUpRequest(@SerializedName("email") val email: String,
                                 @SerializedName("access_token") val accessToken: String,
                                 @SerializedName("name") val firstName: String,
                                 @SerializedName("surname") val lastName: String) : BaseRequest() {
}