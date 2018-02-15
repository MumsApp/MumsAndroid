package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class FacebookSignInRequest(@SerializedName("email") val email: String,
                                 @SerializedName("access_token") val accessToken: String) : BaseRequest()