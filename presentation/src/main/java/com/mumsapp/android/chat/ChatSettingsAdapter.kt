package com.mumsapp.android.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import javax.inject.Inject

class ChatSettingsAdapter : BaseRecyclerViewAdapter<TemplateChatRecipient, ChatSettingsViewHolder> {
    @Inject
    constructor() {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatSettingsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_chat_settings, parent, false)
        return ChatSettingsViewHolder(itemView)
    }

}