package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class SignInRequest(@SerializedName("username") var email: String,
                         @SerializedName("password") var password: String) : BaseRequest()