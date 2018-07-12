package com.mumsapp.android.chat

import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.chat.TemplateChatThread
import javax.inject.Inject

class ChatListAdapter : BaseRecyclerViewAdapter<TemplateChatThread, ChatListViewHolder> {

    private val imagesLoader: ImagesLoader

    @Inject
    constructor(imagesLoader: ImagesLoader) {
        this.imagesLoader = imagesLoader
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView = inflate(parent, R.layout.cell_chat_list)
        return ChatListViewHolder(imagesLoader, itemView)
    }
}