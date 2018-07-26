package com.mumsapp.domain.model.lobby

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseResponse

data class LobbyRoomResponse(@SerializedName("data") var lobbyRoom: LobbyRoom) : BaseResponse()