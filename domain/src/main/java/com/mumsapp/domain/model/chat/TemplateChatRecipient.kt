package com.mumsapp.domain.model.chat

import com.mumsapp.domain.model.BaseResponse
import com.stfalcon.chatkit.commons.models.IUser

data class TemplateChatRecipient(var userId: String, var userName: String) : BaseResponse(), IUser {

    override fun getId() = userId

    override fun getName() = userName

    override fun getAvatar() = ""
}