package com.mumsapp.android.organisation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.organisation.FeedResponse
import javax.inject.Inject

class OrganisationFeedAdapter : BaseRecyclerViewAdapter<FeedResponse, OrganisationFeedViewHolder> {

    private val imagesLoader: ImagesLoader

    @Inject
    constructor(imagesLoader: ImagesLoader) {
        this.imagesLoader = imagesLoader
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganisationFeedViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_organisation_details_feed, parent, false)
        return OrganisationFeedViewHolder(imagesLoader, itemView)
    }
}