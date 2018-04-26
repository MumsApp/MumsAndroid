package com.mumsapp.android.lobby

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyPost
import javax.inject.Inject

class LobbyPostAdapter : BaseRecyclerViewAdapter<LobbyPost, LobbyPostViewHolder> {

    private val imagesLoader: ImagesLoader

    var replyClickListener: ((item: LobbyPost) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader) {
        this.imagesLoader = imagesLoader
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyPostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_lobby_post, parent, false)
        return LobbyPostViewHolder(imagesLoader, itemView)
    }

    override fun onBindViewHolder(holder: LobbyPostViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if (replyClickListener != null) {
            holder.setReplyListener(replyClickListener!!)
        }
    }
}