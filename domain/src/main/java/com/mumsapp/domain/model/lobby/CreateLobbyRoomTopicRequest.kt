package com.mumsapp.domain.model.lobby

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest
import java.io.File

class CreateLobbyRoomTopicRequest(@SerializedName("roomId") var roomId: Int,
                                  @SerializedName("title") var title: String,
                                  @SerializedName("description") var description: String,
                                  @SerializedName("file") var file: File?) : BaseRequest()