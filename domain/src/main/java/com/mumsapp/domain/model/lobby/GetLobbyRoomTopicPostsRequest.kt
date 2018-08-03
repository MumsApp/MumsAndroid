package com.mumsapp.domain.model.lobby

import com.mumsapp.domain.model.BaseRequest

data class GetLobbyRoomTopicPostsRequest(var lobbyRoomId: Int, var lobbyTopicId: Int, var page: Int,
                                         var perPage: Int) : BaseRequest()