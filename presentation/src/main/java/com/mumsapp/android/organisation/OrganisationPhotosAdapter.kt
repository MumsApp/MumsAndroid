package com.mumsapp.android.organisation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.product.ImageSliderItem
import com.mumsapp.android.util.ImagesLoader
import javax.inject.Inject

class OrganisationPhotosAdapter : BaseRecyclerViewAdapter<ImageSliderItem, OrganisationPhotoViewHolder> {

    private val imagesLoader: ImagesLoader

    @Inject
    constructor(imagesLoader: ImagesLoader) {
        this.imagesLoader = imagesLoader
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganisationPhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_organisation_photo, parent, false)
        return OrganisationPhotoViewHolder(imagesLoader, itemView)
    }
}