package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class GoogleSignUpRequest(@SerializedName("email") val email: String,
                               @SerializedName("id_token") val idToken: String,
                               @SerializedName("name") val firstName: String,
                               @SerializedName("surname") val lastName: String) : BaseRequest()