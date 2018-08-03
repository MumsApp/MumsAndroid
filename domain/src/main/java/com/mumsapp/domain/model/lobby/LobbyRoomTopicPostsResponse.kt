package com.mumsapp.domain.model.lobby

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse
import com.mumsapp.domain.model.user.UserMinimalResponse
import org.threeten.bp.ZonedDateTime

data class LobbyRoomTopicPostsResponse(@SerializedName("data") var data: LobbyRoomTopicPostData) : BaseResponse()

data class LobbyRoomTopicPostData(@SerializedName("pages") var pages: Int,
                              @SerializedName("posts") var posts: List<LobbyRoomTopicPost>) : BaseResponse()

data class LobbyRoomTopicPost(@SerializedName("description") var description: String,
                              @SerializedName("creationDate") var creationDate: ZonedDateTime,
                              @SerializedName("author") var author: UserMinimalResponse,
                              @SerializedName("img") var img: String?) : BaseResponse()