package com.mumsapp.domain.model.lobby

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class LobbyResponse(@SerializedName("data") var data: List<LobbyRoom>) : BaseResponse()

data class LobbyRoom(@SerializedName("id") var id: Int,
                     @SerializedName("title") var title: String,
                     @SerializedName("description") var description: String,
                     @SerializedName("isFavourite") var isFavourite: Boolean,
                     @SerializedName("img") var imagePath: String) : BaseResponse()