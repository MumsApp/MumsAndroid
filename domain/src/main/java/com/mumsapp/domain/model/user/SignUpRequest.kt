package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class SignUpRequest(@SerializedName("first_name") var firstName: String,
                         @SerializedName("last_name") var lastName: String,
                         @SerializedName("email") var email: String,
                         @SerializedName("password") var password: String) : BaseRequest() {
}