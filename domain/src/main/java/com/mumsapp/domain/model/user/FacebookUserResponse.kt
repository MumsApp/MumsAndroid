package com.mumsapp.domain.model.user

import com.mumsapp.domain.model.BaseResponse

data class FacebookUserResponse(val firstName: String, val lastName: String, val email: String, val authToken: String) : BaseResponse()