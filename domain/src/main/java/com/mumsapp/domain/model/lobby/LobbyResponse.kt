package com.mumsapp.domain.model.lobby

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class LobbyResponse(@SerializedName("items") var items: List<LobbyItem>) : BaseResponse()

data class LobbyItem(@SerializedName("id") var id: Int,
                     @SerializedName("name") var name: String,
                     @SerializedName("description") var description: String,
                     @SerializedName("joined") var joined: Boolean) : BaseResponse()