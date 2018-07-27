package com.mumsapp.domain.model.lobby

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse
import com.mumsapp.domain.model.user.UserMinimalResponse

data class LobbyRoomTopicsResponse(@SerializedName("data") var data: LobbyRoomTopicData) : BaseResponse()

data class LobbyRoomTopicData(@SerializedName("pages") var pages: Int,
                              @SerializedName("posts") var posts: List<LobbyRoomTopic>) : BaseResponse()

data class LobbyRoomTopic(@SerializedName("title") var title: String,
                          @SerializedName("description") var description: String,
                          @SerializedName("creationDate") var creationDate: Int,
                          @SerializedName("creator") var creator: UserMinimalResponse,
                          @SerializedName("img") var img: String?) : BaseResponse()