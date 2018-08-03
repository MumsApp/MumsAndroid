package com.mumsapp.android.lobby

import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import com.mumsapp.domain.model.lobby.LobbyRoomTopicPost
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.DateManager
import javax.inject.Inject

class LobbyRoomTopicPostsAdapter : BaseRecyclerViewAdapter<LobbyRoomTopicPost, LobbyRoomTopicPostViewHolder> {

    private val imagesLoader: ImagesLoader
    private val dateManager: DateManager
    private val resourceRepository: ResourceRepository

    var userClickListener: ((item: LobbyRoomTopic) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader, dateManager: DateManager,
                resourceRepository: ResourceRepository) {
        this.imagesLoader = imagesLoader
        this.dateManager = dateManager
        this.resourceRepository = resourceRepository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyRoomTopicPostViewHolder {
        val itemView = inflate(parent, R.layout.cell_lobby_post)
        return LobbyRoomTopicPostViewHolder(imagesLoader, itemView, dateManager)
    }

    override fun onBindViewHolder(holder: LobbyRoomTopicPostViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val color = if(position % 2 == 0) {
            resourceRepository.getColor(R.color.white)
        } else {
            resourceRepository.getColor(R.color.blue_extra_light)
        }

        holder.setRootBackground(color)
        holder.setUserClickListener(userClickListener)
    }
}