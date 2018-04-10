package com.mumsapp.android.chat

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.chat.TemplateChatMessage

interface ChatThreadView : LifecycleView {

    fun setTitle(title: String)

    fun showMessages(senderId: String, messages: List<TemplateChatMessage>)
}