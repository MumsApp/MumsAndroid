package com.mumsapp.domain.model.identity

import com.google.gson.annotations.SerializedName

data class Token(@SerializedName("id") val id: Int,
                 @SerializedName("token") val token: String,
                 @SerializedName("refresh_token") val refreshToken: String) {

    fun getAuthHeader(): String {
        return "Bearer $token"
    }
}