package com.mumsapp.android.lobby

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import com.mumsapp.domain.utils.DateManager
import javax.inject.Inject

class LobbyRoomTopicsAdapter : BaseRecyclerViewAdapter<LobbyRoomTopic, LobbyTopicViewHolder> {

    private val imagesLoader: ImagesLoader
    private val dateManager: DateManager

    var replyClickListener: ((item: LobbyRoomTopic) -> Unit)? = null
    var userClickListener: ((item: LobbyRoomTopic) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader, dateManager: DateManager) {
        this.imagesLoader = imagesLoader
        this.dateManager = dateManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyTopicViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_lobby_topic, parent, false)
        return LobbyTopicViewHolder(imagesLoader, itemView, dateManager)
    }

    override fun onBindViewHolder(holder: LobbyTopicViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.setReplyListener(replyClickListener)
        holder.setUserClickistener(userClickListener)
    }
}