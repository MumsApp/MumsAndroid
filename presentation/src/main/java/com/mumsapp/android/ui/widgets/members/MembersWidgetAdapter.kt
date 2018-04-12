package com.mumsapp.android.ui.widgets.members

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.android.util.MAX_VISIBLE_MEMBERS
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class MembersWidgetAdapter : BaseRecyclerViewAdapter<TemplateChatRecipient, MembersWidgetViewHolder> {

    private val imagesLoader: ImagesLoader
    private val resourceRepository: ResourceRepository

    @Inject
    constructor(imagesLoader: ImagesLoader, resourceRepository: ResourceRepository) {
        this.imagesLoader = imagesLoader
        this.resourceRepository = resourceRepository
    }

    override fun getItemCount(): Int {
        return if(items.size > MAX_VISIBLE_MEMBERS) MAX_VISIBLE_MEMBERS + 1 else items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersWidgetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_shop, parent, false)
        return MembersWidgetViewHolder(imagesLoader, itemView)
    }

    override fun onBindViewHolder(holder: MembersWidgetViewHolder, position: Int) {
        if(position < MAX_VISIBLE_MEMBERS) {
            holder.init(items[position])
        } else {
            val text = resourceRepository.getString(R.string.plus_with_number_pattern, items.size - MAX_VISIBLE_MEMBERS)
            holder.init(text)
        }
    }
}