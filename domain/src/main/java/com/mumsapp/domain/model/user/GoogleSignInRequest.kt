package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class GoogleSignInRequest(@SerializedName("email") val email: String,
                          @SerializedName("id_token") val idToken: String) : BaseRequest()