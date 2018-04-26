package com.mumsapp.domain.model.lobby

import com.mumsapp.domain.model.BaseResponse

data class LobbyPost(var id: Int, var userName: String, var date:String, var title: String,
                     val content: String) : BaseResponse()