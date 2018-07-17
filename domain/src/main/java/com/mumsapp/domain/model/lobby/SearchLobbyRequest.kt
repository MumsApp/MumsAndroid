package com.mumsapp.domain.model.lobby

import com.google.gson.annotations.SerializedName
import com.mumsapp.domain.model.BaseRequest

data class SearchLobbyRequest(@SerializedName("searchTerm") var searchTerm: String,
                              @SerializedName("withDescription") var withDescription: Boolean)
    : BaseRequest()