package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class SignUpRequest(@SerializedName("name") var firstName: String,
                         @SerializedName("surname") var lastName: String,
                         @SerializedName("email") var email: String,
                         @SerializedName("password") var password: String) : BaseRequest()