package com.mumsapp.domain.model.user

import com.mumsapp.domain.model.BaseResponse

data class GoogleUserResponse(val firstName: String?, val lastName: String?, val email: String, val authToken: String) : BaseResponse()