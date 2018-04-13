package com.mumsapp.android.organisation

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.product.ImageSliderItem
import com.mumsapp.android.ui.views.BaseImageView
import com.mumsapp.android.util.ImagesLoader

class OrganisationPhotoViewHolder : BaseViewHolder<ImageSliderItem> {

    private val imagesLoader: ImagesLoader

    @BindView(R.id.organisation_photo_cell_image)
    lateinit var imageView: BaseImageView

    constructor(imagesLoader: ImagesLoader, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: ImageSliderItem) {
        if(item.uri != null) {
            imagesLoader.load(item.uri, imageView, 0.5f)
        }
    }
}