package com.mumsapp.android.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mumsapp.domain.model.BaseResponse
import com.mumsapp.domain.model.lobby.LobbyResponse

abstract class BaseRecyclerViewAdapter<T : BaseResponse, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    var items: List<T> = ArrayList()

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.init(items[position])
    }
}