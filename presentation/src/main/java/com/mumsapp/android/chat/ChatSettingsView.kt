package com.mumsapp.android.chat

import com.mumsapp.android.base.BaseView
import com.mumsapp.domain.model.chat.TemplateChatRecipient

interface ChatSettingsView : BaseView {

    fun dismissView()

    fun showItems(items: List<TemplateChatRecipient>)
}