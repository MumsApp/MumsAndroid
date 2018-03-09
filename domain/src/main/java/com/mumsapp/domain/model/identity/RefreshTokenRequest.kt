package com.mumsapp.domain.model.identity

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class RefreshTokenRequest(@SerializedName("refresh_token") var refreshToken: String)
    : BaseRequest()