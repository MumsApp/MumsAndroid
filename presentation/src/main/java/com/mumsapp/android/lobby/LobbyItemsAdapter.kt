package com.mumsapp.android.lobby

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.repository.ImagesRepository
import javax.inject.Inject

class LobbyItemsAdapter : BaseRecyclerViewAdapter<LobbyRoom, LobbyViewHolder> {

    private val imagesLoader: ImagesLoader
    private val imagesRepository: ImagesRepository

    var switchChangeListener: ((item: LobbyRoom, value: Boolean) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader, imagesRepository: ImagesRepository) {
        this.imagesLoader = imagesLoader
        this.imagesRepository = imagesRepository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_lobby, parent, false)
        return LobbyViewHolder(imagesLoader, itemView, imagesRepository)
    }

    override fun onBindViewHolder(holder: LobbyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if (switchChangeListener != null) {
            holder.setCheckedListener(switchChangeListener!!)
        }
    }
}