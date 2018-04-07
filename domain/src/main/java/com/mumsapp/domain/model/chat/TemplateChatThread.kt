package com.mumsapp.domain.model.chat

import com.mumsapp.domain.model.BaseResponse

data class TemplateChatThread(var recipient: TemplateChatRecipient, val date: String, val messages: List<TemplateChatMessage>) : BaseResponse()