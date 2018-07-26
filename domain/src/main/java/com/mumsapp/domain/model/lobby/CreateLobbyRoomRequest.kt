package com.mumsapp.domain.model.lobby

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest
import java.io.File

data class CreateLobbyRoomRequest(@SerializedName("title") var title: String,
                                  @SerializedName("description") var description: String,
                                  @SerializedName("public") var public: Boolean,
                                  @SerializedName("file") var file: File) : BaseRequest()