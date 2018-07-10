package com.mumsapp.domain.model.user

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class UpdateLocationRequest(@SerializedName("name") var name: String?,
                                 @SerializedName("placeID") var placeId: String?,
                                 @SerializedName("lat") var latitude: Double?,
                                 @SerializedName("lon") var longitude: Double?,
                                 @SerializedName("formattedAddress") var formattedAddress: String?,
                                 @SerializedName("enabled") var enabled: Boolean?)
    : BaseRequest()

