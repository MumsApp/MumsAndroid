package com.mumsapp.domain.model.lobby

import com.mumsapp.domain.model.BaseRequest

data class LobbyFavouriteRequest(var lobby: LobbyRoom) : BaseRequest()