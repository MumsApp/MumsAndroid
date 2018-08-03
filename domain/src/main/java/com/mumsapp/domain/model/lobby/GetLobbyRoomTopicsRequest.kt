package com.mumsapp.domain.model.lobby

import com.mumsapp.domain.model.BaseRequest

data class GetLobbyRoomTopicsRequest(var lobbyRoomId: Int, var page: Int, var perPage: Int): BaseRequest()