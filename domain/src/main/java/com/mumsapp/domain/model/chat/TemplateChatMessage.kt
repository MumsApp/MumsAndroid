package com.mumsapp.domain.model.chat

import com.mumsapp.domain.model.BaseResponse

class TemplateChatMessage(var content: String, var isOwnMessage: Boolean) : BaseResponse()