package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class UpdateUserDetailsRequest(@SerializedName("name") var firstName: String,
                                    @SerializedName("surname") var lastName: String,
                                    @SerializedName("description") var description: String)
    : BaseRequest()