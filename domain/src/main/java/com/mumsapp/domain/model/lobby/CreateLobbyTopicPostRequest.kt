package com.mumsapp.domain.model.lobby

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class CreateLobbyTopicPostRequest(@SerializedName("roomId") var roomId: Int,
                                       @SerializedName("topicId") var topicId: Int,
                                       @SerializedName("description") var description: String)
    : BaseRequest()