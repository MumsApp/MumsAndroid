package com.mumsapp.domain.model.lobby

import com.mumsapp.domain.model.BaseRequest

class GetLobbyRoomTopicsRequest(var lobbyRoomId: Int, var page: Int, var perPage: Int): BaseRequest()