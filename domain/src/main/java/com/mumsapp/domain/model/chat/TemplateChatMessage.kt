package com.mumsapp.domain.model.chat

import com.mumsapp.domain.model.BaseResponse
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

class TemplateChatMessage(var messageId: String, var content: String, var date: Date, var user: TemplateChatRecipient) : BaseResponse(), IMessage {

    override fun getId() = messageId

    override fun getText() = content

    override fun getUser() = user as IUser

    override fun getCreatedAt() = date
}

