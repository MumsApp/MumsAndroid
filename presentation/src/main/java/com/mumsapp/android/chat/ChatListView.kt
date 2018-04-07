package com.mumsapp.android.chat

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.chat.TemplateChatThread

interface ChatListView : LifecycleView {

    fun openChatSettingsDialog()

    fun showItems(items: List<TemplateChatThread>)
}