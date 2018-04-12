package com.mumsapp.domain.model.organisation

import com.mumsapp.domain.model.BaseResponse

data class FeedResponse(var avatarUrl: String?, var date: String,var  name: String, var content: String) : BaseResponse() {
}