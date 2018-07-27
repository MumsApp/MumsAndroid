package com.mumsapp.android.lobby

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import com.mumsapp.domain.utils.DateManager
import javax.inject.Inject

class LobbyRoomTopicsAdapter : BaseRecyclerViewAdapter<LobbyRoomTopic, LobbyPostViewHolder> {

    private val imagesLoader: ImagesLoader
    private val dateManager: DateManager

    var replyClickListener: ((item: LobbyRoomTopic) -> Unit)? = null
    var userClickListener: ((item: LobbyRoomTopic) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader, dateManager: DateManager) {
        this.imagesLoader = imagesLoader
        this.dateManager = dateManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyPostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_lobby_post, parent, false)
        return LobbyPostViewHolder(imagesLoader, itemView, dateManager)
    }

    override fun onBindViewHolder(holder: LobbyPostViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.setReplyListener(replyClickListener)
        holder.setUserClickistener(userClickListener)
    }
}