package com.mumsapp.android.lobby

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.lobby.LobbyItem
import javax.inject.Inject

class LobbyItemsAdapter : BaseRecyclerViewAdapter<LobbyItem, LobbyViewHolder> {

    private val imagesLoader: ImagesLoader

    var listener: ((item: LobbyItem, value: Boolean) -> Unit)? = null

    @Inject
    constructor(imagesLoader: ImagesLoader) {
        this.imagesLoader = imagesLoader
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_lobby, parent, false)
        return LobbyViewHolder(imagesLoader, itemView)
    }

    override fun onBindViewHolder(holder: LobbyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if (listener != null) {
            holder.setCheckedListener(listener!!)
        }
    }
}